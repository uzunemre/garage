package com.emreuzun.garage.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Slot {

    private static final long serialVersionUID = 4074374728582967483L;

    private Integer no;

    private boolean empty;

}
