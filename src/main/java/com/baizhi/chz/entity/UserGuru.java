package com.baizhi.chz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGuru {
  @Id
  private String uId;
  private String gId;
}
