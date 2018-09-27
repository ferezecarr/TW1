package ar.edu.unlam.tallerweb1.modelo;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.unlam.tallerweb1.SpringTest;

public class TpTest extends SpringTest {

	private Continente continente , continenteDos , continenteTres;
	private Pais pais , paisDos , paisTres;
	private Ciudad ciudad , ciudadDos , ciudadTres;
	private Ubicacion ubicacion , ubicacionDos , ubicacionTres;
	private Session sesion;
	private List<Pais> paises;
	private List<Ciudad> ciudades;
	

	@Before
	public void init() {
		continente = new Continente();
		continenteDos = new Continente();
		continenteTres = new Continente();
		pais = new Pais();
		paisDos = new Pais();
		paisTres = new Pais();
		ciudad = new Ciudad();
		ciudadDos = new Ciudad();
		ciudadTres = new Ciudad();
		ubicacion = new Ubicacion();
		ubicacionDos = new Ubicacion();
		ubicacionTres = new Ubicacion();
		sesion = getSession();
		paises = new ArrayList<Pais>();
		ciudades = new ArrayList<Ciudad>();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesDeHablaInglesa() {
		pais.setNombre("Inglaterra");
		pais.setIdioma("Ingles");
		
		paisDos.setNombre("España");
		paisDos.setIdioma("Español");
		
		paisTres.setNombre("Estados Unidos");
		paisTres.setIdioma("Ingles");
		
		sesion.save(pais);
		sesion.save(paisDos);
		sesion.save(paisTres);
		
		//--- 1.Buscar todos los paises de habla inglesa ---//
		
		paises = sesion.createCriteria(Pais.class)
				.add(Restrictions.eq("idioma", "Ingles"))
				.list();
		
		assertThat(paises).hasSize(2);
		assertThat(paises).isNotEmpty();
		assertThat(paises.get(0).getIdioma()).isEqualTo("Ingles");
		assertThat(paises.get(0).getNombre()).isEqualTo("Inglaterra");
		assertThat(paises.get(1).getNombre()).isEqualTo("Estados Unidos");
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Rollback
	@Test
	public void testQueBusqueTodosLosPaisesDelContinenteEuropeo() {
		continente.setNombre("America");
		continenteDos.setNombre("Europa");
		continenteTres.setNombre("Asia");
		
		pais.setNombre("Argentina");
		pais.setIdioma("Argentino");
		
		pais.setContinente(continente);
		
		paisDos.setNombre("Alemania");
		paisDos.setIdioma("Aleman");
		
		paisDos.setContinente(continenteDos);
		
		paisTres.setNombre("Rusia");
		paisTres.setIdioma("Ruso");
		
		paisTres.setContinente(continenteTres);
		
		sesion.save(pais);
		sesion.save(paisDos);
		sesion.save(paisTres);
		
		//--- 2.Buscar todos los paises del continente europeo ---//
		
		paises = sesion.createCriteria(Pais.class)
				.createAlias("continente", "con")
				.add(Restrictions.eq("con.nombre", "Europa"))
				.list();
		
		assertThat(paises).hasSize(1);
		assertThat(paises).isNotEmpty();
		assertThat(paises.get(0).getContinente().getNombre()).isEqualTo("Europa");
		assertThat(paises.get(0).getNombre());
		assertThat(paises.get(0).getIdioma());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	@Rollback
	public void testQueBusqueTodosLosPaisesCuyaCapitalEstanAlNorteDelTropicoDeCancer() {
		ubicacion.setLatitud(24D);
		ubicacionDos.setLatitud(37D);
		ubicacionTres.setLatitud(80D);
		
		ciudad.setUbicacionGeografica(ubicacion);
		ciudadDos.setUbicacionGeografica(ubicacionDos);
		ciudadTres.setUbicacionGeografica(ubicacionTres);
		
		pais.setNombre("Egipto");
		paisDos.setNombre("Canada");
		paisTres.setNombre("Inglaterra");
		
		pais.setCapital(ciudad);
		paisDos.setCapital(ciudadDos);
		paisTres.setCapital(ciudadTres);
		
		sesion.save(pais);
		sesion.save(paisDos);
		sesion.save(paisTres);
		
		//--- 3.Buscar todos los paises cuya capital esta al norte del tropico de cancer ---//
		
		paises = sesion.createCriteria(Pais.class)
				.createAlias("capital", "cap")
				.createAlias("cap.ubicacionGeografica", "ubi")
				.add(Restrictions.gt("ubi.latitud", 24D))
				.list();
		
		assertThat(paises).hasSize(2);
		assertThat(paises).isNotEmpty();
		assertThat(paises).isNotNull();
		assertThat(paises.get(0).getNombre()).isEqualTo("Canada");
		assertThat(paises.get(1).getNombre()).isEqualTo("Inglaterra");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	@Rollback
	public void testQueBusqueTodasLasCiudadesDelHemisferioSur() {
		pais.setNombre("Argentina");
		paisDos.setNombre("Chile");
		paisTres.setNombre("Estados Unidos");
		
		ciudad.setNombre("La Boca");
		ciudadDos.setNombre("Temuco");
		ciudadTres.setNombre("New York");
		
		ubicacion.setLatitud(-41D);
		ubicacionDos.setLatitud(-38D);
		ubicacionTres.setLatitud(40D);
		
		ciudad.setPais(pais);
		ciudadDos.setPais(paisDos);
		ciudadTres.setPais(paisTres);
		
		ciudad.setUbicacionGeografica(ubicacion);
		ciudadDos.setUbicacionGeografica(ubicacionDos);
		ciudadTres.setUbicacionGeografica(ubicacionTres);
		
		sesion.save(ciudad);
		sesion.save(ciudadDos);
		sesion.save(ciudadTres);
		
		//--- 4.Buscar todas las ciudades del hemisferio sur ---//
		
		ciudades = sesion.createCriteria(Ciudad.class)
				.createAlias("ubicacionGeografica", "ubi")
				.add(Restrictions.lt("ubi.latitud", 0D))
				.list();
		
		assertThat(ciudades).hasSize(2);
		assertThat(ciudades).isNotEmpty();
		assertThat(ciudades).isNotNull();
		assertThat(ciudades.get(0).getNombre()).isEqualTo("La Boca");
		assertThat(ciudades.get(1).getNombre()).isEqualTo("Temuco");
		
		
	}
}
