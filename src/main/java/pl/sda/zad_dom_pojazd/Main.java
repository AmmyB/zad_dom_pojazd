package pl.sda.zad_dom_pojazd;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Session session = HibernateUtil.INSTANCE.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Podaj co chcesz zrobic? (dodaj/lista/szukaj/usun/aktualizuj)");
            String wykonaj = scanner.nextLine();

            if (wykonaj.equals("dodaj")) {
                System.out.println("Podaj marke");
                String marka = scanner.nextLine();

                System.out.println("Podaj moc");
                double moc = Double.parseDouble(scanner.nextLine());

                System.out.println("Podaj kolor");
                String kolor = scanner.nextLine();

                int rok = 0;
                do {
                    System.out.println("Podaj rok produkcji (1990 - 2020)");
                    rok = Integer.parseInt(scanner.nextLine());
                } while (rok < 1990 || rok > 2020);

                System.out.println("Czy pojazd jest elektryczny? true/false");
                boolean elektryczny = Boolean.parseBoolean(scanner.nextLine());

                Pojazd pojazd = Pojazd.builder()
                        .marka(marka)
                        .moc(moc)
                        .kolor(kolor)
                        .rokProdukcji(rok)
                        .elektryczny(elektryczny)
                        .build();

                session.persist(pojazd);

                transaction.commit();

            } else if (wykonaj.equals("lista")) {
                TypedQuery<Pojazd> zapytanie = session.createQuery("FROM Pojazd", Pojazd.class);
                List<Pojazd> listaWszystkichPojazdow = zapytanie.getResultList();
                for (Pojazd pojazd : listaWszystkichPojazdow) {
                    System.out.println(pojazd);
                }


            } else if (wykonaj.equals("szukaj")) {

                System.out.println("Podaj identyfikator pojazdu: ");
                String odpowiedzId = scanner.nextLine();
                Long pojazdId = Long.parseLong(odpowiedzId);

                Pojazd pojazd = session.get(Pojazd.class, pojazdId);
                if (pojazd == null) {
                    System.err.println("Nie znaleziono pojazdu");
                } else {
                    System.out.println("Pojazd: " + pojazd);
                }

            } else if (wykonaj.equals("usun")) {

                System.out.println("Podaj id pojazdu który chcesz usunać:");
                String id = scanner.nextLine();
                Long pojazdId = Long.parseLong(id);

                Pojazd pojazd = session.get(Pojazd.class, pojazdId);
                if (pojazd == null) {
                    System.err.println("Student nie istnieje.");
                } else {
                    session.remove(pojazd);
                    System.out.println("Prawidłowo usunieto: " + pojazd);
                }

                transaction.commit();

            } else if (wykonaj.equals("aktualizuj")) {

                System.out.println("Podaj id pojazdu który chcesz zaktualizować:");
                String id = scanner.nextLine();
                Long pojazdId = Long.parseLong(id);

                Pojazd pojazd = session.get(Pojazd.class, pojazdId);
                if (pojazd == null) {
                    System.err.println("Pojazd nie istnieje.");
                } else {
                    System.out.println("Podaj marke");
                    String marka = scanner.nextLine();

                    System.out.println("Podaj moc");
                    double moc = Double.parseDouble(scanner.nextLine());

                    System.out.println("Podaj kolor");
                    String kolor = scanner.nextLine();

                    int rok = 0;
                    do {
                        System.out.println("Podaj rok produkcji (1990 - 2020");
                        rok = Integer.parseInt(scanner.nextLine());
                    } while (rok < 1990 || rok > 2020);

                    System.out.println("Czy pojazd jest elektryczny? true/false");
                    boolean elektryczny = Boolean.parseBoolean(scanner.nextLine());

                    Pojazd pojazd1 = Pojazd.builder()
                            .marka(marka)
                            .moc(moc)
                            .kolor(kolor)
                            .rokProdukcji(rok)
                            .elektryczny(elektryczny)
                            .id(pojazdId)
                            .build();

                    session.merge(pojazd1);

                    transaction.commit();

                }

            }
            transaction.commit();
        } catch (Exception ioe) {

        }

    }
}
