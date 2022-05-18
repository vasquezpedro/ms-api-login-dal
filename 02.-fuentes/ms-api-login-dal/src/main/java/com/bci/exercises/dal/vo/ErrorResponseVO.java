package com.bci.exercises.dal.vo;

import java.util.List;

public class ErrorResponseVO {

    private List<ErrorVO> error;

    public ErrorResponseVO(List<ErrorVO> error){
        this.error=error;
    }
    public List<ErrorVO> getError() {
        return this.error;
    }

    public void setError(List<ErrorVO> error) {
        this.error = error;
    }

    
}
