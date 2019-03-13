package com.doms.automation.jaxbclasses.taskqueue;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;



/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="AvailableDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Createprogid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Createts" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Createuserid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DataKey" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="DataType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HoldFlag" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Lockid" type="{http://www.w3.org/2001/XMLSchema}String" />
 *       &lt;attribute name="Modifyprogid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Modifyts" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Modifyuserid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TaskQKey" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="TransactionKey" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "TaskQueue")
public class TaskQueue {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "AvailableDate")
    @XmlSchemaType(name = "dateTime")
    protected String availableDate;
    @XmlAttribute(name = "Createprogid")
    protected String createprogid;
    @XmlAttribute(name = "Createts")
    @XmlSchemaType(name = "dateTime")
    protected String createts;
    @XmlAttribute(name = "Createuserid")
    protected String createuserid;
    @XmlAttribute(name = "DataKey")
    protected String dataKey;
    @XmlAttribute(name = "DataType")
    protected String dataType;
    @XmlAttribute(name = "HoldFlag")
    protected String holdFlag;
    @XmlAttribute(name = "Lockid")
    protected String lockid;
    @XmlAttribute(name = "Modifyprogid")
    protected String modifyprogid;
    @XmlAttribute(name = "Modifyts")
    @XmlSchemaType(name = "dateTime")
    protected String modifyts;
    @XmlAttribute(name = "Modifyuserid")
    protected String modifyuserid;
    @XmlAttribute(name = "TaskQKey")
    protected String taskQKey;
    @XmlAttribute(name = "TransactionKey")
    protected String transactionKey;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the availableDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvailableDate() {
        return availableDate;
    }

    /**
     * Sets the value of the availableDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvailableDate(String value) {
        this.availableDate = value;
    }

    /**
     * Gets the value of the createprogid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateprogid() {
        return createprogid;
    }

    /**
     * Sets the value of the createprogid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateprogid(String value) {
        this.createprogid = value;
    }

    /**
     * Gets the value of the createts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatets() {
        return createts;
    }

    /**
     * Sets the value of the createts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatets(String value) {
        this.createts = value;
    }

    /**
     * Gets the value of the createuserid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateuserid() {
        return createuserid;
    }

    /**
     * Sets the value of the createuserid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateuserid(String value) {
        this.createuserid = value;
    }

    /**
     * Gets the value of the dataKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataKey() {
        return dataKey;
    }

    /**
     * Sets the value of the dataKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataKey(String value) {
        this.dataKey = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the holdFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoldFlag() {
        return holdFlag;
    }

    /**
     * Sets the value of the holdFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoldFlag(String value) {
        this.holdFlag = value;
    }

    /**
     * Gets the value of the lockid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockid() {
        return lockid;
    }

    /**
     * Sets the value of the lockid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockid(String value) {
        this.lockid = value;
    }

    /**
     * Gets the value of the modifyprogid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifyprogid() {
        return modifyprogid;
    }

    /**
     * Sets the value of the modifyprogid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifyprogid(String value) {
        this.modifyprogid = value;
    }

    /**
     * Gets the value of the modifyts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifyts() {
        return modifyts;
    }

    /**
     * Sets the value of the modifyts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifyts(String value) {
        this.modifyts = value;
    }

    /**
     * Gets the value of the modifyuserid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * Sets the value of the modifyuserid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifyuserid(String value) {
        this.modifyuserid = value;
    }

    /**
     * Gets the value of the taskQKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskQKey() {
        return taskQKey;
    }

    /**
     * Sets the value of the taskQKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskQKey(String value) {
        this.taskQKey = value;
    }

    /**
     * Gets the value of the transactionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionKey() {
        return transactionKey;
    }

    /**
     * Sets the value of the transactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionKey(String value) {
        this.transactionKey = value;
    }

}
