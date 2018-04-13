package ru.ifree.msgoperators.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties("new")

@Entity
@Table(name="contacts")
public class Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contact")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$|^\\+?[0-9]{1,15}$"
    ,message = "invalid")//^\+[1-9]{1}[0-9]{1,14}$| //^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$|
    private String contact;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "custom")
    @NotNull
    private Boolean custom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "connector_id")
    @JsonIgnore
    private Connector connector;

    public Contact() {
    }

    public Contact(Contact contact){
        this.id = contact.getId();
        this.contact = contact.getContact();
        this.description = contact.getDescription();
        this.custom = contact.getCustom();
    }

    public Contact(Integer id,String contact, String description,Boolean custom) {
        this.id = id;
        this.contact = contact;
        this.description = description;
        this.custom = custom;
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
        return custom;
    }

    public void setCustom(Boolean isCustom) {
        this.custom = isCustom;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public boolean isNew() {
        return (getId() == null);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", description='" + description + '\'' +
                ", custom=" + custom +
                ", connector=" + connector +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        Contact that = (Contact) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }
}
