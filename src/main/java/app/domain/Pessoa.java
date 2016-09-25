package app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String nome;
	
	private Integer idade;
	
	private Date dataNascimento;
	
	private CorCabelo corCabelo;
	
	private boolean empregada;
	
	private Cargo cargo;

	public Pessoa() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public CorCabelo getCorCabelo() {
		return corCabelo;
	}

	public void setCorCabelo(CorCabelo corCabelo) {
		this.corCabelo = corCabelo;
	}

	
	public boolean getEmpregada() {
		return empregada;
	}

	public void setEmpregada(boolean empregada) {
		this.empregada = empregada;
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((corCabelo == null) ? 0 : corCabelo.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + (empregada ? 1231 : 1237);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((idade == null) ? 0 : idade.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (cargo != other.cargo)
			return false;
		if (corCabelo != other.corCabelo)
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (empregada != other.empregada)
			return false;
		if (id != other.id)
			return false;
		if (idade == null) {
			if (other.idade != null)
				return false;
		} else if (!idade.equals(other.idade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pessoa [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", idade=");
		builder.append(idade);
		builder.append(", dataNascimento=");
		builder.append(dataNascimento);
		builder.append(", corCabelo=");
		builder.append(corCabelo);
		builder.append(", empregada=");
		builder.append(empregada);
		builder.append(", cargo=");
		builder.append(cargo);
		builder.append("]");
		return builder.toString();
	}
	
}
