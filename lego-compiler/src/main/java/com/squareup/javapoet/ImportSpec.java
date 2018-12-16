package com.squareup.javapoet;

public class ImportSpec {
    public final String importSpec;

    private ImportSpec(Builder builder){
        this.importSpec = builder.importSpec;
    }

    public static final class Builder{
        public final String importSpec;

        public Builder(String importSpec) {
            this.importSpec = importSpec;
        }

        public ImportSpec build(){
            return new ImportSpec(this);
        }
    }
}
