 
package org.mcbx.database.relationship.test;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
/*
This Class is Auto-Generated, To Set-UP.
On "Window-->Services" Create new (Dabatase) if you don't have one and Connect into your "Database".
Then for creating this Entity Class.
On you Project Source Package Create (New-->Other...) (Category-->Persistence) (File Types-->Entity Class from Database).
Then a little bit modification for your Constructor for creating an Object. Up to you.
Or Google it on how to create Entity Class from your databases using netbeans or any flatform.
Remove this if too annoying. (T_T)
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address")
    private Collection<Employee> employeeCollection;

    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }

    public Address(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Employee> getEmployeeCollection() {
        return employeeCollection;
    }

    public void setEmployeeCollection(Collection<Employee> employeeCollection) {
        this.employeeCollection = employeeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.mcbx.database.relationship.test.Address[ id=" + id + " ]";
    }

}
