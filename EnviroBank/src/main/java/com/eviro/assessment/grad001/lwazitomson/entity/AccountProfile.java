package com.eviro.assessment.grad001.lwazitomson.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountProfile {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountHolderName;
    private String accountHolderSurname;
    private String httpImageLink;
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
