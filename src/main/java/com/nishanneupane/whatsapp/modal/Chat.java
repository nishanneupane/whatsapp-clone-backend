package com.nishanneupane.whatsapp.modal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="chat")
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String chat_name;
	private String chat_image;
	
	@ManyToMany
	private Set<User> admins=new HashSet<>();
	
	@Column(name="is-group")
	private boolean isGroup;
	
	@JoinColumn(name="createdBy")
	@ManyToOne
	private User createdBy;
	
	@ManyToMany
	private Set<User> users=new HashSet<>();
	
	@OneToMany
	private List<Message> messages=new ArrayList<>();

}
