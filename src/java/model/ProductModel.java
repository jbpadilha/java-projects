package java.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class ProductModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="parent_product_id")
	private ProductModel produto_parent;
	
	@OneToMany(mappedBy="produto_parent")
	private Set<ProductModel> product_children = new HashSet<ProductModel>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductModel getProduto_parent() {
		return produto_parent;
	}

	public void setProduto_parent(ProductModel produto_parent) {
		this.produto_parent = produto_parent;
	}

	public Set<ProductModel> getProduct_children() {
		return product_children;
	}

	public void setProduct_children(Set<ProductModel> product_children) {
		this.product_children = product_children;
	}
	
	
	
}
