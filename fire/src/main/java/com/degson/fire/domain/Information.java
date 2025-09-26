package com.degson.fire.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Information {
    @JsonProperty("qrContent")
    private String qrContent;
    @JsonProperty("fireId")
    private Long fireId;
}