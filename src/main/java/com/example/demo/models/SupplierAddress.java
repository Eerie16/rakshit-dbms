package com.example.demo.models;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;

 /**
  * SupplierAddress Value Object.
  * This class is value object representing database table SupplierAddress
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



public class SupplierAddress implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private int supplierId;
    private String address;



    /** 
     * Constructors. DaoGen generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public SupplierAddress () {

    }

    public SupplierAddress (int supplierIdIn, String addressIn) {

          this.supplierId = supplierIdIn;
          this.address = addressIn;

    }


    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public int getSupplierId() {
          return this.supplierId;
    }
    public void setSupplierId(int supplierIdIn) {
          this.supplierId = supplierIdIn;
    }

    public String getAddress() {
          return this.address;
    }
    public void setAddress(String addressIn) {
          this.address = addressIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variales, without going trough the 
     * individual set-methods.
     */

    public void setAll(int supplierIdIn,
          String addressIn) {
          this.supplierId = supplierIdIn;
          this.address = addressIn;
    }


    /** 
     * hasEqualMapping-method will compare two SupplierAddress instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(SupplierAddress valueObject) {

          if (valueObject.getSupplierId() != this.supplierId) {
                    return(false);
          }
          if (this.address == null) {
                    if (valueObject.getAddress() != null)
                           return(false);
          } else if (!this.address.equals(valueObject.getAddress())) {
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
        StringBuffer out = new StringBuffer(this.getDaogenVersion());
        out.append("\nclass SupplierAddress, mapping to table SupplierAddress\n");
        out.append("Persistent attributes: \n"); 
        out.append("supplierId = " + this.supplierId + "\n"); 
        out.append("address = " + this.address + "\n"); 
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the retuned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        SupplierAddress cloned = new SupplierAddress();

        cloned.setSupplierId(this.supplierId); 
        if (this.address != null)
             cloned.setAddress(new String(this.address)); 
        return cloned;
    }



    /** 
     * getDaogenVersion will return information about
     * generator which created these sources.
     */
    public String getDaogenVersion() {
        return "DaoGen version 2.4.1";
    }

}
