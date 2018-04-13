package ru.ifree.msgoperators.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.Hibernate;




import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@JsonIgnoreProperties("new")

@Entity
@Table(name = "connectors")
public class Connector{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "system_id")
    private String systemId;

    @Column(name = "smsc_addr")
    @NotNull
    private String smscAddr;

    @Column
    @NotNull
    private Integer port;

    @Column
    @NotNull
    private Boolean enabled;

    @Column(name = "jur_person")
    private String jurName;

    @OneToMany(mappedBy = "connector",fetch = FetchType.EAGER)
    private Set<Contact> contacts;

    public Connector(Integer id, String name, String systemId, Integer port,Boolean enabled,String smscAddr, String jurName){
        this.id = id;
        this.name = name;
        this.systemId = systemId;
        this.smscAddr = smscAddr;
        this.port = port;
        this.enabled = enabled;
        this.jurName = jurName;
    }

    public Connector(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSmscAddr() {
        return smscAddr;
    }

    public void setSmscAddr(String smscAddr) {
        this.smscAddr = smscAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getJurName() {
        return jurName;
    }

    public void setJurName(String jurName) {
        this.jurName = jurName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }


    public boolean isNew() {
        return (getId() == null);
    }

    @Override
    public String toString() {
        return "Connector{" +
                "id="+ id + '\'' +
                ", name='" + name + '\'' +
                ", systemId='" + systemId + '\'' +
                ", smscAddr='" + smscAddr + '\'' +
                ", port=" + port +
                ", enabled=" + enabled +
                ", jurName='" + jurName + '\'' +
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
        Connector that = (Connector) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }

}
