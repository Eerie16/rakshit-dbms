package com.example.demo.models;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;

 /**
  * RawMaterial Value Object.
  * This class is value object representing database table RawMaterial
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



public class RawMaterial implements Cloneable, Serializable {

    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private int rawMaterialId;
    private String name;
    private Integer price;



    /** 
     * Constructors. DaoGen generates two constructors by default.
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key of the corresponding table.
     */

    public RawMaterial () {

    }

    public RawMaterial (int rawMaterialIdIn) {

          this.rawMaterialId = rawMaterialIdIn;

    }


    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     */

    public int getRawMaterialId() {
          return this.rawMaterialId;
    }
    public void setRawMaterialId(int rawMaterialIdIn) {
          this.rawMaterialId = rawMaterialIdIn;
    }

    public String getName() {
          return this.name;
    }
    public void setName(String nameIn) {
          this.name = nameIn;
    }

    public Integer getPrice() {
          return this.price;
    }
    public void setPrice(Integer priceIn) {
          this.price = priceIn;
    }



    /** 
     * setAll allows to set all persistent variables in one method call.
     * This is useful, when all data is available and it is needed to 
     * set the initial state of this object. Note that this method will
     * directly modify instance variales, without going trough the 
     * individual set-methods.
     */

    public void setAll(int rawMaterialIdIn,
          String nameIn,
          Integer priceIn) {
          this.rawMaterialId = rawMaterialIdIn;
          this.name = nameIn;
          this.price = priceIn;
    }


    /** 
     * hasEqualMapping-method will compare two RawMaterial instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     */
    public boolean hasEqualMapping(RawMaterial valueObject) {

          if (valueObject.getRawMaterialId() != this.rawMaterialId) {
                    return(false);
          }
          if (this.name == null) {
                    if (valueObject.getName() != null)
                           return(false);
          } else if (!this.name.equals(valueObject.getName())) {
                    return(false);
          }
          if (this.price == null) {
                    if (valueObject.getPrice() != null)
                           return(false);
          } else if (!this.price.equals(valueObject.getPrice())) {
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
        out.append("\nclass RawMaterial, mapping to table RawMaterial\n");
        out.append("Persistent attributes: \n"); 
        out.append("rawMaterialId = " + this.rawMaterialId + "\n"); 
        out.append("name = " + this.name + "\n"); 
        out.append("price = " + this.price + "\n"); 
        return out.toString();
    }


    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the retuned cloned object
     * will also have all its attributes cloned.
     */
    public Object clone() {
        RawMaterial cloned = new RawMaterial();

        cloned.setRawMaterialId(this.rawMaterialId); 
        if (this.name != null)
             cloned.setName(new String(this.name)); 
        if (this.price != null)
             cloned.setPrice(new Integer(this.price.intValue())); 
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