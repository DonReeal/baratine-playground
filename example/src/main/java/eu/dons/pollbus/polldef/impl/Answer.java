package eu.dons.pollbus.polldef.impl;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
class Answer {
   
    @Setter(AccessLevel.PACKAGE)
    private int answerId;
    @Setter(AccessLevel.PACKAGE)
    private String name;
    @Setter(AccessLevel.PACKAGE)
    private int sortKey;

}
