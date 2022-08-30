package com.unknown.data.entity.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.unknown.data.entity.comm.CommEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name= "USER_MST")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity extends CommEntity {

	@Id
	private long seq;

	private String loginId;

	private String name;

	private String pass;

	private int passFailCnt;

	private String email;
}
