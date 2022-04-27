package mind.blower.home;



public class Payment_show {

    private String name;
    private String imageUrl;


    private String key;



    public Payment_show(String name,String key) {
        this.name = name;
        this.key=key;
    }

    public Payment_show() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
