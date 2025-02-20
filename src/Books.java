public class Books {
    private int id;
    private String title;
    private String author;
    private boolean available;

    //construktornnn
    public Books(int id, String title, String author, boolean available){
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    //getter och setters
    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public boolean getAvailable(){
        return available;
    }

    //tostring hehe
    @Override
    public String toString(){
        return "Title:" + title + ", Author: "+author+", availability: "+available;
    }
}
