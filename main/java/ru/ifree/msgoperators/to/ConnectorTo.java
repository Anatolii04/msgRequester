package ru.ifree.msgoperators.to;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ConnectorTo implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private String systemId;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$" +
            "|^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$" +
            "|^(https?|ftp|file):\\/\\/[-a-zA-Z0-9+&@#\\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\\/%=~_|]",
            message = "must be ip or dns address or URL")
    private String smscAddr;

    @NotNull
    @Range(min = 1,max = 65535, message = " must between 1 and 65535 characters")
    private Integer port;

    @NotBlank
    @Pattern(regexp = "(true|false)")
    private String enabled;

    @NotBlank
    private String jurName;

    public ConnectorTo(Integer id, String name, String systemId, String smscAddr, Integer port, String enabled, String jurName) {
        this.id = id;
        this.name = name;
        this.systemId = systemId;
        this.smscAddr = smscAddr;
        this.port = port;
        this.enabled = enabled;
        this.jurName = jurName;
    }

    public ConnectorTo(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getJurName() {
        return jurName;
    }

    public void setJurName(String jurName) {
        this.jurName = jurName;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "ConnectorTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", systemId='" + systemId + '\'' +
                ", smscAddr='" + smscAddr + '\'' +
                ", port=" + port +
                ", enabled=" + enabled +
                ", jurName='" + jurName + '\'' +
                '}';
    }
}
