package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "order.all", query = "Select o from Orders o"),
        @NamedQuery(name = "order.byId", query = "Select o from Orders o where o.id = :id")
})
public class Orders {
	
    private Long id;
    private double price = 0.0;
    private Date date = new Date();
    private Boolean sold = false;

    private List<Waffle> waffles = new ArrayList<Waffle>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    
    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Waffle> getWaffles() {
        return waffles;
    }
    public void setWaffles(List<Waffle> waffles) {
        this.waffles = waffles;
    }
}