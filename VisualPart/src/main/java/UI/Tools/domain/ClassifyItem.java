package UI.Tools.domain;

/**
 * Date:2020/3/23
 * Time:16:37
 * author:wenjun
 */
public class ClassifyItem {
    //分类id
    private String id;
    //分类描述
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClassifyItem(){}
    public ClassifyItem(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
