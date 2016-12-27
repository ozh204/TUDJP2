package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "order.all", query = "Select o from Order o"),
        @NamedQuery(name = "order.byId", query = "Select o from Order o where o.id = :id")
})
public class Order
{
    private Integer id;
    private double price = 0.0;
    private Date date;
    private Boolean sold = false;

    Date now = new Date();
    private List<Waffle> waffles = new ArrayList<Waffle>();

    public Order() {
        super();
        date = now;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Waffle> getWaffles() {
        return waffles;
    }

    public void setWaffles(List<Waffle> waffles) {
        this.waffles = waffles;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }
}