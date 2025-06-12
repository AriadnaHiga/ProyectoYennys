public enum categoriaLibro {
    FICCION("Ficción"),
    NO_FICCION("No ficción"),
    INFANTIL("Infantil"),
    ACADEMICO("Académico"),
    AUTOAYUDA("Autoayuda"),
    NOVELA("Novela"),
    JUVENIL("Juvenil"),
    CIENCIA_FICCION("Ciencia ficción"),
    FANTASIA("Fantasía"),
    HISTORICO("Histórico"),
    MISTERIO("Misterio"),
    TERROR("Terror"),
    ROMANCE("Romance"),
    PSICOLOGIA("Psicología"),
    EDUCACION("Educación"),
    BIOGRAFIA("Biografía"),
    ENSAYO("Ensayo"),
    POESIA("Poesía"),
    DRAMA("Drama"),
    AVENTURA("Aventura"),
    COMIC("Cómic"),
    DIDACTICO("Didáctico"),
    TECNICO("Técnico"),
    RELIGION("Religión"),
    GASTRONOMIA("Gastronomía"),
    VIAJES("Viajes"),
    SALUD("Salud"),
    POLITICA("Política"),
    NEGOCIOS("Negocios"),
    ARTE("Arte"),
    CULTURA("Cultura"),
    DEPORTES("Deportes"),
    HISTORIA("Historia"),
    LITERATURA_CLASICA("Literatura clásica");

    private final String nombreDB;

    categoriaLibro(String nombreDB) {
        this.nombreDB = nombreDB;
    }

    public String getNombreDB() {
        return nombreDB;
    }
}
