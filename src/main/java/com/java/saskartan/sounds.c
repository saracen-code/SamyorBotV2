/*
**	SOUNDS.C
**
**	Sound Change Applier
**
**      Copyright (C) 2000 by Mark Rosenfelder.
**      This program may be freely used and modified for non-commercial purposes.
**      See http://www.zompist.com/sounds.htm for documentation.
*/

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

#define TRUE 1
#define FALSE 0

static int printRules = 0;
static int bracketOut = 0;
static int printSourc = 1;
static int toScreen   = 1;

#define MAXRULE 200
#define MAXCAT  50


static int  nRule = 0;
static char *Rule[MAXRULE];

static int  nCat = 0;
static char *Cat[MAXCAT];

/*
**	ReadRules
**
**	Read in the rules file *.sc for a given project.
**
**	There are two types of rules: sound changes and category definitions.
**	The former are stored in Rule[], the latter in Cat[].
**
**	The format of these rules is given under Transform().
*/
int ReadRules(const char *inputString)
{
    char buffer[129];
    char *s;
    int n;
    const char *ptr = inputString;

    // Reset counters
    nRule = 0;
    nCat = 0;

    while (*ptr)
    {
        // Copy the next line of text into the buffer
        int i = 0;
        while (*ptr && *ptr != '\n' && i < 128)
        {
            buffer[i++] = *ptr++;
        }
        buffer[i] = '\0'; // Null-terminate the string

        // Skip the newline character
        if (*ptr == '\n') ptr++;

        // Skip empty lines
        if (strlen(buffer) == 0)
            continue;

        s = malloc(strlen(buffer) + 1); // Allocate memory for the rule/category string
        if (s)
            strcpy(s, buffer);

        // Process the line based on its content
        if (buffer[0] != '*')
        {
            if (strchr(buffer, '/')) // If '/' is found, it's a rule
                Rule[nRule++] = s;
            else if (strchr(buffer, '=')) // If '=' is found, it's a category
                Cat[nCat++] = s;
        }
    }

    // Print categories and rules for debugging
    if (nCat)
    {
        printf("%i categories found\n", nCat);

        #ifdef PRINT_RULES
        for (n = 0; n < nCat; n++)
            printf("%s\n", Cat[n]);
        printf("\n");
        #endif
    }
    else
        printf("No categories were found.\n\n");

    if (nRule)
    {
        printf("%i rules found\n", nRule);

        #ifdef PRINT_RULES
        for (n = 0; n < nRule; n++)
            printf("%s\n", Rule[n]);
        printf("\n");
        #endif
    }
    else
        printf("No rules were found.\n\n");

    return nRule;
} /* ReadRules */



/*
**	Divide
**
**	Divide a rule into source and target phoneme(s) and environment.
**	That is, for a rule s1/s2/env
**	create the three null-terminated strings s1, s2, and env.
**
**	If this cannot be done, return FALSE.
*/
int Divide( char *Rule, char **s1, char **s2, char **env )
{
	size_t i;
	static char s1_str[20];
	static char s2_str[20];
	static char ev_str[50];
	
	i = strcspn( Rule, "/" );
	if (i == 0 || i > 19)
		return(FALSE);
		
	strncpy( s1_str, Rule, i );
	s1_str[i] = '\0';
	Rule += i + 1;
	
	i = strcspn( Rule, "/" );
	if (i > 19)
		return(FALSE);
	
	if (i)	
		strncpy( s2_str, Rule, i );
	s2_str[i] = '\0';
	Rule += i + 1;
	
	strcpy( ev_str, Rule );

	*s1  = s1_str;
	*s2  = s2_str;
	*env = ev_str;
	
	return(TRUE);
	
} /*Divide*/


/*
**	TryCat
**
**	See if a particular phoneme sequence is part of any category.
**	(We try all the categories.)
**
**	For instance, if we have 'a' in the source word and 'V' in the
**	structural description, and a category V=aeiou, TryCat returns TRUE,
**	and sets *n to the number of characters to skip.
**	
**	If we had 'b' instead, TryCat would return FALSE instead.
**
**	If no category with the given identification (env) can be found,
**	we return TRUE (continue looking), but set *n to 0.
**
**	Warning: For now, we don't have a way to handle digraphs.
**
**	We also return TRUE if 
*/
int TryCat( char *env, char *word, int *n, int *catLoc )
{
	int c;
	char *catdef;
	
	if (*word == '\0')
		return(FALSE);
		
	for (c = 0; c < nCat; c++)
	{
		if (*env == *Cat[c])
		{
			catdef = strchr( Cat[c], '=' );
			
			if (strchr( catdef + 1, word[0] ))
			{
				*n = 1;
				*catLoc = strchr( Cat[c], word[0] ) - Cat[c];
				return(TRUE);
			}
			else
				return(FALSE);
		}
	}

	*n = 0;
	return(TRUE);	
	
} /*TryCat*/

/*
**	TryRule
**
**	See if a rule s1->s2/env applies at position i in the given word.
**
**	If it does, we pass back the index where s1 was found in the
**	word, as well as s1 and s2, and return TRUE.
**
**	Otherwise, we return FALSE, and pass garbage in the output variables.
*/
int TryRule( char *word, int i, char *Rule, int *n, char **s1, char **s2, char *varRep )
{
	int j, m, cont = 0;
	int catLoc;
	char *env;
	int  optional = FALSE;
	*varRep = '\0';
	
	if (!Divide( Rule, s1, s2, &env ) || !strchr( env, '_' ))
		return(FALSE);
	
	for (j = 0, cont = TRUE; cont && j < strlen(env); j++)
	{
		switch( env[j] )
		{
			case '(':
				optional = TRUE;
				break;

			case ')':
				optional = FALSE;
				break;

			case '#':
				cont = j ? (i == strlen(word)) : (i == 0);
				break;
				
			case '_':
				cont = !strncmp( &word[i], *s1, strlen(*s1) );
				if (cont)
				{
					*n = i;
					i += strlen(*s1);
				}
				else
				{
				  cont = TryCat( *s1, &word[i], &m, &catLoc );
				  if (cont && m)
				  {
					int c;
				    *n = i;
				    i += m;

					for (c = 0; c < nCat; c++)
						if ((*s2)[0] == Cat[c][0] && catLoc < strlen(Cat[c]))
							*varRep = Cat[c][catLoc];
				   }
				   else if (cont)
				     cont = FALSE;
				}
				break;
			
			default:
				cont = TryCat( &env[j], &word[i], &m, &catLoc );
				
				if (cont && !m)
				{
					/* no category applied */
					
					cont = i < strlen(word) && word[i] == env[j];

					m = 1;
				}
				if (cont)
					i += m;

				if (!cont && optional)
					cont = TRUE;
		}
	}
	
	if (cont && printRules)
		printf( "   %s->%s /%s applies to %s at %i\n", 
		*s1, *s2, env, word, *n );
	
	return(cont);

} /*TryRule*/

/*
**	Transform
**
**	Apply the rules to a single word and return the result.
**
**	The rules are stated in the form string1/string2/environment, e.g.
**		f/h/#_V
**	which states that f changes to h at the beginning of a word before a 
**	vowel.
*/
char *Transform( char *input )
{
	       char inword[80];
	static char outword[80];

	char instr[10];
	char *s1, *s2;
	int i;
	int r;
	int n;
	
	strcpy( inword, input );
	
	/* Try to apply each rule in turn */ 
	
	for (r = 0; r < nRule; r++)
	{
		/* Initialize output of this rule to null */
		
		memset( outword, 0, 80 );	
		
		/* Check each position of the input word in turn */
		 
		i = 0;
		while (i < strlen(inword))
		{
			char varRep = 0;

			if (TryRule( inword, i, Rule[r], &n, &s1, &s2, &varRep ))
			{
				/* Rule applies at inword[n] */
				
				if (n)
					strncat( outword, &inword[i], n - i );
			
				if (varRep)
					outword[strlen(outword)] = varRep;
				else if (strlen(s2))
					strcat( outword, s2 );
				
				i = n + strlen(s1);
			} 
			else
			{
				/* Rule doesn't apply at this location */
				
				outword[strlen(outword)] = inword[i++];
			}
		}
		
		/* Output of one rule is input to next one */
		
		strcpy( inword, outword );
	}
	
	/* Return the output of the last rule */
	
	return(outword);
	
} /*Transform*/
	
/*
**	DoWords
**
**	Read in each word in turn from the input file,
**	transform it according to the rules,
**	and output it to the output file.
**
**	This algorithm ensures that word files of any size can be processed.
*/
char* DoWords(const char *inputString)
{
    char inword[84];
    char *outword;
    size_t outputLength = 0;
    char *outputBuffer = malloc(1); // Start with an empty string

    // Check if the allocation was successful
    if (outputBuffer == NULL)
    {
        printf("Memory allocation failed.\n");
        return NULL;
    }

    // Copy the input string into a local variable
    const char *ptr = inputString;

    while (sscanf(ptr, "%83s", inword) == 1)  // Read each word
    {
        ptr += strlen(inword) + 1;  // Move the pointer to the next word

        outword = Transform(inword);  // Assuming Transform() is defined elsewhere

        // Set the output word to the last transformed word
        outputBuffer = realloc(outputBuffer, strlen(outword) + 1);
        if (outputBuffer == NULL)
        {
            printf("Memory allocation failed.\n");
            return NULL;
        }

        strcpy(outputBuffer, outword); // Store the last processed word

        // Optionally print to screen
        if (toScreen)  // Assuming toScreen is defined
        {
            printf("%s\n", outword);
        }
    }

    return outputBuffer;  // Return the last processed word
}  /*DoWords*/


/*
**	MAIN ROUTINE
**
**	Ask for name of project
**	Read in rules and input words
**	Apply transformations
**	Output words
**
*/
main(int argc, char **argv)
{
    const char *inputString =
                "V=aeiou\n"
                "C=ptcqbdgmnlrhs\n"
                "F=ie\n"
                "B=ou\n"
                "S=ptc\n"
                "Z=bdg\n"
                "s//_#\n"
                "m//_#\n"
                "e//Vr_#\n"
                "v//V_V\n"
                "u/o/_#\n"
                "gn/nh/_\n"
                "S/Z/V_V\n"
                "c/i/F_t\n"
                "c/u/B_t\n"
                "p//V_t\n"
                "ii/i/_\n"
                "e//C_rV\n";;

    int once = FALSE;
    char lexicon[65] = "\0";

    /* Read command line arguments */
    int i;
    for (i = 1; i < argc; i++)
    {
        if (argv[i][0] == '-' && strlen(argv[i]) > 1)
        {
            switch (argv[i][1])
            {
            case 'p': case 'P':   printRules = 1;  break;
            case 'b': case 'B':   bracketOut = 1;  break;
            case 'l': case 'L':   printSourc = 0;  break;
            case 'f': case 'F':   toScreen   = 0;  break;
            }
        }
        else if (!lexicon[0])
            strcpy(lexicon, argv[i]);
    }

    once = lexicon[0];

    printf("\nSOUND CHANGE APPLIER\n(C) 1992,2000 by Mark Rosenfelder\nFor more information see www.zompist.com\n\n");

    if (once)
    {
        printf("Applying rules to %s\n\n", lexicon);

        if (ReadRules(*inputString)) {
            char *result = DoWords(lexicon);
            printf("%s", result);  // Only print the processed word(s)
            free(result);  // Don't forget to free the dynamically allocated memory
        }
    }
    else
    {
        int done = FALSE;
        while (!done)
        {
            printf("\nEnter the word you want to change.\n\n");
            printf("Enter q to quit the program.\n-->");

            fgets(lexicon, 65, stdin);

            if (strlen(lexicon))
                lexicon[strlen(lexicon) - 1] = '\0';

            if (!strcmp(lexicon, "q"))
                done = TRUE;

            if (ReadRules(inputString))
                DoWords(lexicon);  // Only output the final processed word here
                done = TRUE;
        }
    }

    return 0;
}