package ar.edu.unlam.tallerweb1.modelo;
import javax.persistence.*;

@Entity
public class Ciudad {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	@OneToOne(cascade= {CascadeType.ALL})
	private Ubicacion ubicacionGeografica;
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Pais pais;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public Ubicacion getUbicacionGeografica() {
		return ubicacionGeografica;
	}
	public void setUbicacionGeografica(Ubicacion ubicacionGeografica) {
		this.ubicacionGeografica=ubicacionGeografica;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais=pais;
	}
}