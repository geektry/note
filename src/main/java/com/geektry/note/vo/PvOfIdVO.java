package com.geektry.note.vo;

/**
 * @author Chaohang Fu
 */
public class PvOfIdVO {

    private Long id;
    private String title;
    private Long pv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PvOfIdVO{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", pv=").append(pv);
        sb.append('}');
        return sb.toString();
    }
}
