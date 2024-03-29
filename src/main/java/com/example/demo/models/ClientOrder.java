package com.example.demo.models;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;

 /**
  * ClientOrder Value Object.
  * This class is value object representing database table ClientOrder
  * This class is intented to be used together with associated Dao object.
  */

 /**
  * This sourcecode has been generated by FREE DaoGen generator version 2.4.1.
  * The usage of generated code is restricted to OpenSource software projects
  * only. DaoGen is available in http://titaniclinux.net/daogen/
  * It has been programmed by Tuomo Lukka, Tuomo.Lukka@iki.fi
  *
  * DaoGen license: The following DaoGen generated source code is licensed
  * under the terms of GNU GPL license. The full text for license is available
  * in GNU project's pages: http://www.gnu.org/copyleft/gpl.html
  *
  * If you wish to use the DaoGen generator to produce code for closed-source
  * commercial applications, you must pay the lisence fee. The price is
  * 5 USD or 5 Eur for each database table, you are generating code for.
  * (That includes unlimited amount of iterations with all supported languages
  * for each database table you are paying for.) Send mail to
  * "Tuomo.Lukka@iki.fi" for more information. Thank you!
  */



public class ClientOrder implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private int clientOrderId;
    private java.sql.Date receivedDate;
    private java.sql.Date dueDate;
    private String status;
    private String remark;
    private Integer priority;
    private Integer clientId;
    private Integer approvedByEmployeeId;
    private Integer assignedToEmployeeId;



    /** 
     * Constructors. DaoGen generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public ClientOrder () {

    }

    public ClientOrder (int clientOrderIdIn) {

          this.clientOrderId = clientOrderIdIn;

    }


    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public int getClientOrderId() {
          return this.clientOrderId;
    }
    public void setClientOrderId(int clientOrderIdIn) {
          this.clientOrderId = clientOrderIdIn;
    }

    public java.sql.Date getReceivedDate() {
          return this.receivedDate;
    }
    public void setReceivedDate(java.sql.Date receivedDateIn) {
          this.receivedDate = receivedDateIn;
    }

    public java.sql.Date getDueDate() {
          return this.dueDate;
    }
    public void setDueDate(java.sql.Date dueDateIn) {
          this.dueDate = dueDateIn;
    }

    public String getStatus() {
          return this.status;
    }
    public void setStatus(String statusIn) {
          this.status = statusIn;
    }

    public String getRemark() {
          return this.remark;
    }
    public void setRemark(String remarkIn) {
          this.remark = remarkIn;
    }

    public Integer getPriority() {
          return this.priority;
    }
    public void setPriority(Integer priorityIn) {
          this.priority = priorityIn;
    }

    public Integer getClientId() {
          return this.clientId;
    }
    public void setClientId(Integer clientIdIn) {
          this.clientId = clientIdIn;
    }

    public Integer getApprovedByEmployeeId() {
          return this.approvedByEmployeeId;
    }
    public void setApprovedByEmployeeId(Integer approvedByEmployeeIdIn) {
          this.approvedByEmployeeId = approvedByEmployeeIdIn;
    }

    public Integer getAssignedToEmployeeId() {
          return this.assignedToEmployeeId;
    }
    public void setAssignedToEmployeeId(Integer assignedToEmployeeIdIn) {
          this.assignedToEmployeeId = assignedToEmployeeIdIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variales, without going trough the 
     * individual set-methods.
     */

    public void setAll(int clientOrderIdIn,
          java.sql.Date receivedDateIn,
          java.sql.Date dueDateIn,
          String statusIn,
          String remarkIn,
          Integer priorityIn,
          Integer clientIdIn,
          Integer approvedByEmployeeIdIn,
          Integer assignedToEmployeeIdIn) {
          this.clientOrderId = clientOrderIdIn;
          this.receivedDate = receivedDateIn;
          this.dueDate = dueDateIn;
          this.status = statusIn;
          this.remark = remarkIn;
          this.priority = priorityIn;
          this.clientId = clientIdIn;
          this.approvedByEmployeeId = approvedByEmployeeIdIn;
          this.assignedToEmployeeId = assignedToEmployeeIdIn;
    }


    /** 
     * hasEqualMapping-method will compare two ClientOrder instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(ClientOrder valueObject) {

          if (valueObject.getClientOrderId() != this.clientOrderId) {
                    return(false);
          }
          if (this.receivedDate == null) {
                    if (valueObject.getReceivedDate() != null)
                           return(false);
          } else if (!this.receivedDate.equals(valueObject.getReceivedDate())) {
                    return(false);
          }
          if (this.dueDate == null) {
                    if (valueObject.getDueDate() != null)
                           return(false);
          } else if (!this.dueDate.equals(valueObject.getDueDate())) {
                    return(false);
          }
          if (this.status == null) {
                    if (valueObject.getStatus() != null)
                           return(false);
          } else if (!this.status.equals(valueObject.getStatus())) {
                    return(false);
          }
          if (this.remark == null) {
                    if (valueObject.getRemark() != null)
                           return(false);
          } else if (!this.remark.equals(valueObject.getRemark())) {
                    return(false);
          }
          if (this.priority == null) {
                    if (valueObject.getPriority() != null)
                           return(false);
          } else if (!this.priority.equals(valueObject.getPriority())) {
                    return(false);
          }
          if (this.clientId == null) {
                    if (valueObject.getClientId() != null)
                           return(false);
          } else if (!this.clientId.equals(valueObject.getClientId())) {
                    return(false);
          }
          if (this.approvedByEmployeeId == null) {
                    if (valueObject.getApprovedByEmployeeId() != null)
                           return(false);
          } else if (!this.approvedByEmployeeId.equals(valueObject.getApprovedByEmployeeId())) {
                    return(false);
          }
          if (this.assignedToEmployeeId == null) {
                    if (valueObject.getAssignedToEmployeeId() != null)
                           return(false);
          } else if (!this.assignedToEmployeeId.equals(valueObject.getAssignedToEmployeeId())) {
                    return(false);
          }

          return true;
    }



    /**
     * toString will return String object representing the state of this 
     * valueObject. This is useful during application development, and 
     * possibly when application is writing object states in textlog.
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("\nclass ClientOrder, mapping to table ClientOrder\n");
        out.append("Persistent attributes: \n"); 
        out.append("clientOrderId = " + this.clientOrderId + "\n"); 
        out.append("receivedDate = " + this.receivedDate + "\n"); 
        out.append("dueDate = " + this.dueDate + "\n"); 
        out.append("status = " + this.status + "\n"); 
        out.append("remark = " + this.remark + "\n"); 
        out.append("priority = " + this.priority + "\n"); 
        out.append("clientId = " + this.clientId + "\n"); 
        out.append("approvedByEmployeeId = " + this.approvedByEmployeeId + "\n"); 
        out.append("assignedToEmployeeId = " + this.assignedToEmployeeId + "\n"); 
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the retuned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        ClientOrder cloned = new ClientOrder();

        cloned.setClientOrderId(this.clientOrderId); 
        if (this.receivedDate != null)
             cloned.setReceivedDate((java.sql.Date)this.receivedDate.clone()); 
        if (this.dueDate != null)
             cloned.setDueDate((java.sql.Date)this.dueDate.clone()); 
        if (this.status != null)
             cloned.setStatus(new String(this.status)); 
        if (this.remark != null)
             cloned.setRemark(new String(this.remark)); 
        if (this.priority != null)
             cloned.setPriority(new Integer(this.priority.intValue())); 
        if (this.clientId != null)
             cloned.setClientId(new Integer(this.clientId.intValue())); 
        if (this.approvedByEmployeeId != null)
             cloned.setApprovedByEmployeeId(new Integer(this.approvedByEmployeeId.intValue())); 
        if (this.assignedToEmployeeId != null)
             cloned.setAssignedToEmployeeId(new Integer(this.assignedToEmployeeId.intValue())); 
        return cloned;
    }




}