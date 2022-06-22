package br.com.infotera.santander.financiamento.model;

public class File {

    private String bsContent;

    private String nmFile;

    public File() {
    }

    public File(String bsContent, String nmFile) {
        this.bsContent = bsContent;
        this.nmFile = nmFile;
    }

    public String getBsContent() {
        return bsContent;
    }

    public void setBsContent(String bsContent) {
        this.bsContent = bsContent;
    }

    public String getNmFile() {
        return nmFile;
    }

    public void setNmFile(String nmFile) {
        this.nmFile = nmFile;
    }
}
