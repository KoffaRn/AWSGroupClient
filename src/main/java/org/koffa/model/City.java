package org.koffa.model;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class City {
    private int cityId;
    private String cityName;
    private int id;
}
