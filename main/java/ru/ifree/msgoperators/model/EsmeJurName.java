package ru.ifree.msgoperators.model;


public class EsmeJurName {
    private Integer connectorId;
    private String jurName;

    public EsmeJurName(Integer connectorId, String jurName) {
        this.connectorId = connectorId;
        this.jurName = jurName;
    }

    public EsmeJurName(){}

    public Integer getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(Integer connectorId) {
        this.connectorId = connectorId;
    }

    public String getJurName() {
        return jurName;
    }

    public void setJurName(String jurName) {
        this.jurName = jurName;
    }

    @Override
    public String toString() {
        return "EsmeJurName{" +
                "connectorId=" + connectorId +
                ", jurName='" + jurName + '\'' +
                '}';
    }
}
