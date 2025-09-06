package org.com.imaapi.application.dto.consulta.input;

public class ConsultaCountDto {
    private Integer count;

    public ConsultaCountDto() {
    }

    public ConsultaCountDto(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}