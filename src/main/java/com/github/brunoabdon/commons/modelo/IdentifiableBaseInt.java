package com.github.brunoabdon.commons.modelo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IdentifiableBaseInt implements Identifiable<Integer> {

    private Integer id;


}
