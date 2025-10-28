package com.wayne.waynesecurity.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wayne.waynesecurity.model.enums.AccessArea;
import com.wayne.waynesecurity.model.enums.AccessResult;
import com.wayne.waynesecurity.model.enums.AccessType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_accesslog")
public class AccessLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccessArea area;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccessType type;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccessResult result;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	public AccessLog() {
	}

	public AccessLog(Long id, AccessArea area, AccessType type, AccessResult result, Instant moment, User user) {
		this.id = id;
		this.area = area;
		this.type = type;
		this.result = result;
		this.moment = moment;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccessArea getArea() {
		return area;
	}

	public void setArea(AccessArea area) {
		this.area = area;
	}

	public AccessType getType() {
		return type;
	}

	public void setType(AccessType type) {
		this.type = type;
	}

	public AccessResult getResult() {
		return result;
	}

	public void setResult(AccessResult result) {
		this.result = result;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccessLog other = (AccessLog) obj;
		return Objects.equals(id, other.id);
	}
}
