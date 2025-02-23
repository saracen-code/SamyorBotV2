package samyorbot.cogs.SamyorClasses;

public class ProvinceList {
    private Country country;
    private String name;
    private DLNode<Province> provinceList = new DLNode<>();

    public ProvinceList(DLNode<Province> provinceList) {
        this.provinceList = provinceList;
    }

    // Get
    public void getProvinceList() {
        provinceList.displayForward();
    }

    // Add
    public void addProvince(Province province) {
        this.provinceList.addLast(province);
    }
    public void removeProvince(Province province) {
        DLNode.Node<Province> node = provinceList.getHead();
        while(node != null && node.next != null) {
            int i = 0;
            if (node.data == province) {
                provinceList.removeAtIndex(i);
                System.out.println("Successfully removed province.");
                break;
            }
            node = node.next;
            i++;
        }
    }

    // Search for a province
    public Province searchProvince(String provinceName) {
        DLNode.Node<Province> node = provinceList.getHead();
        while (node != null) {
            if (node.data.getName().equals(provinceName)) {
                System.out.println("Province found: " + node.data.getName());
                return node.data;  // Return the matching province
            }
            node = node.next;
        }
        System.out.println("Province not found.");
        return null;  // Return null if not found
    }
}
