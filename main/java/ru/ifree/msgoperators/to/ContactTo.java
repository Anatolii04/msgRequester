package ru.ifree.msgoperators.to;


import org.hibernate.validator.constraints.NotBlank;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ContactTo{

    private Integer id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$|^\\+?[0-9]{1,15}$"
            ,message = "invalid")//^\+[1-9]{1}[0-9]{1,14}$| //^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$|
    private String contact;

    @NotBlank
    private String description;

    private Boolean isCustom;


    public ContactTo() {
    }

    public ContactTo(Integer id,String contact, String description,Boolean isCustom) {
        this.id = id;
        this.contact = contact;
        this.description = description;
        this.isCustom = isCustom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCustom() {
        return isCustom;
    }

    public void setCustom(Boolean isCustom) {
        this.isCustom = isCustom;
    }

    public boolean isNew() {
        return (getId() == null);
    }

    @Override
    public String toString() {
        return "ContactTo{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", description='" + description + '\'' +
                ", isCustom=" + isCustom +
                '}';
    }
}
