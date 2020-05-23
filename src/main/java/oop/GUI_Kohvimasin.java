package oop;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class GUI_Kohvimasin extends Application {

    private Scene Menüü1, Menüü2, Menüü3, Menüü4, SuurusJaMakse, Valmis, Üllatusleht, Üllatusmakse, ÜllatusValmis;

    private int count;
    private double kohvijookHind;

    private final int suurusX = 718;
    private final int suurusY = 930;

    //logifaili jaoks
    private int number;
    private String suurusTops;
    private double hind;

    //üllatuse jaoks
    private Random random;

    @Override
    public void init() {
        random = new Random();
    }


    //kas on midagi maksevälja sisestatud ning kas see on ka õige
    public void tekstiKontroll(Button button, TextField tekst, String numbrid) {
        tekst.setOnKeyTyped(actionEvent -> {
            boolean onNumber = tekst.getText().matches(numbrid);
            boolean onTühi = tekst.getText().isEmpty();
            if (onNumber && !onTühi) button.setDisable(false);
            else button.setDisable(true);
        });
    }

    public void mängiMuusika(String asukoht) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioSisend = AudioSystem.getAudioInputStream(new File(asukoht).getAbsoluteFile());
        Clip pala = AudioSystem.getClip();
        pala.open(audioSisend);
        pala.start();
    }

    // Meetod kirjutab tekstifaili kuupäeva koos kellaaja, jooginumbri ja suurusega, mis kasutaja valis
    public void kirjutLogi() throws IOException {

        SimpleDateFormat vormer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date kuupäev = new Date(System.currentTimeMillis());
        BufferedWriter puhvertatudKir = null;

        try {
            FileWriter kirjutaja = new FileWriter("logifail.txt", true);
            puhvertatudKir = new BufferedWriter(kirjutaja);
            puhvertatudKir.write(vormer.format(kuupäev) + number + " " + suurusTops + "\n");
        }
        catch (IOException e) {
            System.out.println("Sisestamisel tekkis viga failis 'logifail.txt'");
        }
        finally {
            if(puhvertatudKir != null) {
                puhvertatudKir.close();
            }
        }
    }

    @Override
    public void start(Stage peaLava) throws Exception {

        //Kohvimasinas olevad joogid koos väikese topsi hindadega
        JoogidjaHinnad mustKohv = new JoogidjaHinnad("Must kohv", 1.60, 1);
        JoogidjaHinnad cappuccino = new JoogidjaHinnad("Cappuccino", 2.20, 2);
        JoogidjaHinnad caffeLatte = new JoogidjaHinnad("Caffe latte", 1.80, 3);
        JoogidjaHinnad espresso = new JoogidjaHinnad("Espresso", 2.50, 4);
        JoogidjaHinnad flatWhite = new JoogidjaHinnad("Flat White", 2.3, 5);
        JoogidjaHinnad longBlack = new JoogidjaHinnad("Long Black", 2.4, 6);
        JoogidjaHinnad latteArt = new JoogidjaHinnad("Latte Art", 2.95, 7);
        JoogidjaHinnad cafeAuLait = new JoogidjaHinnad("Cafe au Lait", 2.70, 8);
        JoogidjaHinnad affogato = new JoogidjaHinnad("Affogato", 1.5, 9); //muudatus
        JoogidjaHinnad chaiLatte = new JoogidjaHinnad("Chai latte", 2.65, 10);
        JoogidjaHinnad mocha = new JoogidjaHinnad("Mocha", 2.35, 11);
        JoogidjaHinnad kakao = new JoogidjaHinnad("Kakao", 1.20, 12);
        JoogidjaHinnad keeduvesi = new JoogidjaHinnad("Keeduvesi", 0.0, 13);

        List<JoogidjaHinnad> kohvijoogid = Arrays.asList(mustKohv, cappuccino, caffeLatte, espresso, flatWhite,
                longBlack, latteArt, cafeAuLait, affogato, chaiLatte, mocha, kakao, keeduvesi, keeduvesi, keeduvesi);

        Image pilt1 = new Image("file:läbipaistev1.png");
        ImageView menüüPilt = new ImageView(pilt1);

        Image pilt2 = new Image("file:läbipaistev2.png");
        ImageView taust1 = new ImageView(pilt2);

        Image pilt3 = new Image("file:läbipaistev2.png");
        ImageView taust2 = new ImageView(pilt3);

        Image pilt4 = new Image("file:läbipaistev2.png");
        ImageView taust3 = new ImageView(pilt3);

        Image pilt5 = new Image("file:läbipaistev2.png");
        ImageView taust4 = new ImageView(pilt5);

        Image pilt6 = new Image("file:läbipaistev2.png");
        ImageView taust5 = new ImageView(pilt6);

        Image pilt7 = new Image("file:läbipaistev2.png");
        ImageView taust6 = new ImageView(pilt7);

        Image pilt8 = new Image("file:läbipaistev2.png");
        ImageView taust7 = new ImageView(pilt8);

        Image iMakseÜllatus = new Image("file:läbipaistev2.png");
        ImageView ivMakseÜllatus = new ImageView(iMakseÜllatus);

        Image pilt9 = new Image("file:läbipaistev2.png");
        ImageView taust8 = new ImageView(pilt9);

        // Avaleht - menüü ja edasi vahelehtedele liikumine
        Group juur1 = new Group(menüüPilt);

        Menüü1 = new Scene(juur1, suurusX, suurusY);

        Button edasiVahelehed = new Button("");
        edasiVahelehed.setOnMouseClicked(mouseEvent -> peaLava.setScene(Menüü2));
        edasiVahelehed.setOnMouseEntered(MouseEvent -> {
            DropShadow vari = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            edasiVahelehed.setEffect(vari);
        });
        edasiVahelehed.setOnMouseExited(mouseEvent -> {
            edasiVahelehed.setEffect(null);
        });
        edasiVahelehed.setLayoutX(640);
        edasiVahelehed.setLayoutY(850);

        edasiVahelehed.setMinSize(62, 52);
        edasiVahelehed.setMaxSize(62, 52);

        Image iEdasiVl = new Image("file:edasi.png", edasiVahelehed.getWidth(), edasiVahelehed.getHeight(), false, true, true);
        BackgroundImage bgiEdasiVl = new BackgroundImage(iEdasiVl, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(edasiVahelehed.getWidth(), edasiVahelehed.getHeight(), true, true, true, false));

        Background bgEdasiVl = new Background(bgiEdasiVl);
        edasiVahelehed.setBackground(bgEdasiVl);

        juur1.getChildren().addAll(edasiVahelehed);

        peaLava.setTitle("Kohvimasin");
        peaLava.setResizable(false);
        peaLava.getIcons().add(new Image("file:icon.png"));
        peaLava.setScene(Menüü1);
        peaLava.show();

        // Vaheleht 3 - Üllatusleht, kus on 3 nuppu, igale nupule on seatud erinev funktsioon, üks nupp hüppab ringi, üks nupp kaob vajutades ära, üks nupp on õige
        //on ees pool selleks, et kui keegi siinseid nuppe liigutab, saab nad pärast mõlemat makse varianti ka tagasi oma kohale panna
        Group juur4 = new Group(taust4);

        Menüü4 = new Scene(juur4, suurusX, suurusY);

        Button tagasiVahelehelt_3 = new Button("");
        tagasiVahelehelt_3.setOnMouseClicked(mouseEvent -> peaLava.setScene(Menüü3));
        tagasiVahelehelt_3.setOnMouseEntered(mouseEvent -> {
            DropShadow vari5 = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            tagasiVahelehelt_3.setEffect(vari5);
        });
        tagasiVahelehelt_3.setOnMouseExited(mouseEvent -> tagasiVahelehelt_3.setEffect(null));

        tagasiVahelehelt_3.setLayoutX(16);
        tagasiVahelehelt_3.setLayoutY(850);
        tagasiVahelehelt_3.setMinSize(62, 52);
        tagasiVahelehelt_3.setMaxSize(62, 52);

        Image iTagasiVl_3 = new Image("file:tagasi.png", tagasiVahelehelt_3.getWidth(), tagasiVahelehelt_3.getHeight(), false, true, true);
        BackgroundImage bgiTagasiVl_3 = new BackgroundImage(iTagasiVl_3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(tagasiVahelehelt_3.getWidth(), tagasiVahelehelt_3.getHeight(), true, true, true, false));

        Background bgTagasiVl_3 = new Background(bgiTagasiVl_3);
        tagasiVahelehelt_3.setBackground(bgTagasiVl_3);

        Button Üllatus = new Button();

        Üllatus.setMinSize(200, 200);
        Üllatus.setMaxSize(200, 200);

        Image iÜllatus = new Image("file:üllatus.png", Üllatus.getWidth(), Üllatus.getHeight(), false, true, true);
        BackgroundImage bgiÜllatus = new BackgroundImage(iÜllatus, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(Üllatus.getWidth(), Üllatus.getHeight(), true, true, true, false));

        Background bgÜllatus = new Background(bgiÜllatus);
        Üllatus.setBackground(bgÜllatus);

        Üllatus.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(Üllatusleht);
            number = 13;
            hind = 2.30;
        });
        Üllatus.setOnMouseEntered(MouseEvent -> {
            DropShadow variÜll = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            Üllatus.setEffect(variÜll);
        });
        Üllatus.setOnMouseExited(mouseEvent -> {
            Üllatus.setEffect(null);
        });
        Üllatus.setLayoutX(275);
        Üllatus.setLayoutY(325);

        Button ÜllatusLiikuv = new Button();

        ÜllatusLiikuv.setMinSize(200, 200);
        ÜllatusLiikuv.setMaxSize(200, 200);

        Image iÜllatusLiikuv = new Image("file:üllatus.png", ÜllatusLiikuv.getWidth(), ÜllatusLiikuv.getHeight(), false, true, true);
        BackgroundImage bgiÜllatusLiikuv = new BackgroundImage(iÜllatusLiikuv, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(ÜllatusLiikuv.getWidth(), ÜllatusLiikuv.getHeight(), true, true, true, false));

        Background bgÜllatusLiikuv = new Background(bgiÜllatusLiikuv);
        ÜllatusLiikuv.setBackground(bgÜllatusLiikuv);


        ÜllatusLiikuv.setLayoutX(275);
        ÜllatusLiikuv.setLayoutY(100);

        ÜllatusLiikuv.setOnMouseEntered(e -> {
            ÜllatusLiikuv.setLayoutX(random.nextInt((int) suurusX-200));
            ÜllatusLiikuv.setLayoutY(random.nextInt((int) suurusY-200));
        });

        Button ÜllatusVõlts = new Button();

        ÜllatusVõlts.setMinSize(200, 200);
        ÜllatusVõlts.setMaxSize(200, 200);

        Image iÜllatusVõlts = new Image("file:üllatus.png", ÜllatusVõlts.getWidth(), ÜllatusVõlts.getHeight(), false, true, true);
        BackgroundImage bgiÜllatusVõlts = new BackgroundImage(iÜllatusVõlts, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(ÜllatusVõlts.getWidth(), ÜllatusVõlts.getHeight(), true, true, true, false));

        Background bgÜllatusVõlts = new Background(bgiÜllatusVõlts);
        ÜllatusVõlts.setBackground(bgÜllatusVõlts);

        ÜllatusVõlts.setLayoutX(275);
        ÜllatusVõlts.setLayoutY(550);

        ÜllatusVõlts.setOnMouseEntered(MouseEvent -> {
            DropShadow variÜll = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            ÜllatusVõlts.setEffect(variÜll);
        });
        ÜllatusVõlts.setOnMouseExited(mouseEvent -> {
            ÜllatusVõlts.setEffect(null);
        });

        FadeTransition võlts = new FadeTransition(Duration.seconds(3), ÜllatusVõlts);
        võlts.setFromValue(0.99);
        võlts.setToValue(0.0);
        võlts.setCycleCount(1);

        ÜllatusVõlts.setOnAction(event -> {
            võlts.play();
            võlts.setOnFinished(event1 -> {
                FadeTransition võlts2 = new FadeTransition(Duration.seconds(3), ÜllatusVõlts);
                võlts2.setFromValue(0.0);
                võlts2.setToValue(0.99);
                võlts2.setCycleCount(1);
                ÜllatusVõlts.setVisible(false);
                ÜllatusVõlts.setDisable(true);
                võlts2.play();
            });
        });

        juur4.getChildren().addAll(Üllatus, ÜllatusLiikuv, ÜllatusVõlts, tagasiVahelehelt_3);

        //SuurusJaMakse - võimalik valida 3 suuruse seast üks, sisestada nõutav summa, maksta joogi eest vajutades nuppu maksa
        Group juur5 = new Group(taust5);
        SuurusJaMakse = new Scene(juur5, suurusX, suurusY);

        Button small = new Button();
        small.setLayoutX(108);
        small.setLayoutY(400);
        small.setPrefSize(70, 40);

        Image ismall = new Image("file:vali.png", small.getWidth(), small.getHeight(), false, true, true);
        BackgroundImage bgismall = new BackgroundImage(ismall, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(small.getWidth(), small.getHeight(), true, true, true, false));

        Background bgsmall= new Background(bgismall);
        small.setBackground(bgsmall);

        small.setOnMouseEntered(MouseEvent -> {
            DropShadow vari111 = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            small.setEffect(vari111);
        });
        small.setOnMouseExited(mouseEvent -> {
            small.setEffect(null);
        });

        Button medium = new Button();
        medium.setLayoutX(314);
        medium.setLayoutY(400);
        medium.setPrefSize(70, 40);

        Image imedium = new Image("file:vali.png", medium.getWidth(), medium.getHeight(), false, true, true);
        BackgroundImage bgimedium = new BackgroundImage(imedium, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(medium.getWidth(), medium.getHeight(), true, true, true, false));

        Background bgmedium= new Background(bgimedium);
        medium.setBackground(bgmedium);

        medium.setOnMouseEntered(MouseEvent -> {
            DropShadow vari112 = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            medium.setEffect(vari112);
        });
        medium.setOnMouseExited(mouseEvent -> {
            medium.setEffect(null);
        });

        Button large = new Button();
        large.setLayoutX(522);
        large.setLayoutY(400);
        large.setPrefSize(70, 40);

        Image ilarge = new Image("file:vali.png", large.getWidth(), large.getHeight(), false, true, true);
        BackgroundImage bgilarge = new BackgroundImage(ilarge, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(large.getWidth(), large.getHeight(), true, true, true, false));

        Background bglarge= new Background(bgilarge);
        large.setBackground(bglarge);

        large.setOnMouseEntered(MouseEvent -> {
            DropShadow vari113 = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            large.setEffect(vari113);
        });
        large.setOnMouseExited(mouseEvent -> {
            large.setEffect(null);
        });

        TextField kasutajaSisestatudSumma = new TextField();
        kasutajaSisestatudSumma.setLayoutX(235);
        kasutajaSisestatudSumma.setLayoutY(551);
        kasutajaSisestatudSumma.setPrefSize(250,25);
        kasutajaSisestatudSumma.setDisable(true);

        Button maksa = new Button();
        maksa.setLayoutX(510);
        maksa.setLayoutY(543);
        maksa.setPrefSize(90, 40);
        maksa.setDisable(true);

        Image iMaksa = new Image("file:maksa.png", maksa.getWidth(), maksa.getHeight(), false, true, true);
        BackgroundImage bgiMaksa = new BackgroundImage(iMaksa, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(maksa.getWidth(), maksa.getHeight(), true, true, true, false));

        Background bgMaksa= new Background(bgiMaksa);
        maksa.setBackground(bgMaksa);

        maksa.setOnMouseEntered(MouseEvent -> {
            DropShadow vari11 = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            maksa.setEffect(vari11);
        });
        maksa.setOnMouseExited(mouseEvent -> {
            maksa.setEffect(null);
        });

        Image iSuurused = new Image("file:SuurusedL.png");
        ImageView vaade = new ImageView();
        vaade.setImage(iSuurused);
        vaade.setFitHeight(370);
        vaade.setFitWidth(590);
        vaade.setLayoutX(55);
        vaade.setLayoutY(20);

        Label summaSisend = new Label("Tasuge valitud joogi eest:");
        summaSisend.setLayoutX(30);
        summaSisend.setLayoutY(550);
        summaSisend.setPrefSize(200,25);
        summaSisend.setFont(Font.font("aldo", FontWeight.BOLD, 16));

        Label teade2 = new Label("");
        teade2.setLayoutX(235);
        teade2.setLayoutY(490);
        teade2.setPrefSize(300,25);
        teade2.setFont(Font.font("aldo", FontWeight.BOLD, 16));

        Label teade3 = new Label("");
        teade3.setLayoutX(315);
        teade3.setLayoutY(610);
        teade3.setPrefSize(300,25);
        teade3.setFont(Font.font("aldo", FontWeight.BOLD, 16));

        Label tagastus = new Label("");
        tagastus.setLayoutX(270);
        tagastus.setLayoutY(620);
        tagastus.setPrefSize(300,25);
        tagastus.setFont(Font.font("aldo", FontWeight.BOLD, 16));

        // Nupp viib tagasi menüüsse ja lähtestab muutujad
        Button MenüüsseMakse = new Button();

        MenüüsseMakse.setMinSize(73, 73);
        MenüüsseMakse.setMaxSize(73, 73);

        Image iMenüüsseMakse = new Image("file:menu.png", MenüüsseMakse.getWidth(), MenüüsseMakse.getHeight(), false, true, true);
        BackgroundImage bgiMenüüsseMakse = new BackgroundImage(iMenüüsseMakse, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(MenüüsseMakse.getWidth(), MenüüsseMakse.getHeight(), true, true, true, false));

        Background bgMenüüsseMakse = new Background(bgiMenüüsseMakse);
        MenüüsseMakse.setBackground(bgMenüüsseMakse);

        MenüüsseMakse.setOnMouseEntered(mouseEvent2 -> {
            DropShadow borderGlow= new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.BLACK);
            MenüüsseMakse.setEffect(borderGlow);
        });
        MenüüsseMakse.setOnMouseExited(mouseEvent2 -> MenüüsseMakse.setEffect(null));

        MenüüsseMakse.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(Menüü1);
            teade2.setText("");
            teade3.setText("");
            kasutajaSisestatudSumma.clear();
            count = 0;
            medium.setDisable(false);
            large.setDisable(false);
        });
        MenüüsseMakse.setLayoutX(16);
        MenüüsseMakse.setLayoutY(840);

        small.setOnAction(e -> {
            kasutajaSisestatudSumma.setDisable(false);
            kohvijookHind = hind; // siin tuleb siis võtta kindla joogi hind ja vastavalt sellega manipuleerida
            teade2.setText("Teie valitud jook maksab " + kohvijookHind + " eurot.");
            teade2.setVisible(true);
            suurusTops = "s";
            count = 0;
        });

        medium.setOnAction(e -> {
            kasutajaSisestatudSumma.setDisable(false);
            kohvijookHind = Math.round((hind * 1.2) * 10.0) / 10.0;
            teade2.setText("Teie valitud jook maksab " + kohvijookHind + " eurot.");
            teade2.setVisible(true);
            suurusTops = "m";
            count = 0;
        });

        large.setOnAction(e -> {
            kasutajaSisestatudSumma.setDisable(false);
            kohvijookHind = Math.round((hind * 1.3) * 10.0) / 10.0;
            teade2.setText("Teie valitud jook maksab " + kohvijookHind + " eurot.");
            teade2.setVisible(true);
            suurusTops = "l";
            count = 0;
        });

        tekstiKontroll(maksa, kasutajaSisestatudSumma, "(\\d+.)|(\\d+(\\.\\d+)?)");

        maksa.setOnAction(e -> {
            teade3.setVisible(true);
            double sisestatudSumma = Double.parseDouble(kasutajaSisestatudSumma.getText());
            if (sisestatudSumma>= kohvijookHind){
                count = 0;
                teade3.setText("Makse edukas!");
                teade3.setLayoutX(300);
                teade3.setLayoutY(590);
                MenüüsseMakse.setVisible(false);
                if (sisestatudSumma > kohvijookHind) { // kui kliendi sisestatud raha on > kui joogi lõplik hind
                    double tagasi = 0.0;
                    tagasi = Math.round((sisestatudSumma - kohvijookHind) * 100.0) / 100.0;
                    tagastus.setText("Saate tagasi " + tagasi + " eurot.");
                    tagastus.setVisible(true);
                }
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent actionEvent) {
                    }
                }));
                timeline.setCycleCount(1);
                timeline.setOnFinished(actionEvent -> {
                    peaLava.setScene(Valmis);
                    try {
                        mängiMuusika("Masin.wav");
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedAudioFileException ex) {
                        ex.printStackTrace();
                    }

                });
                timeline.play();

                //alles siis minnakse valmis lehele, kui on makstud
                Group juur6 = new Group(taust6);
                Valmis = new Scene(juur6, suurusX, suurusY);

                Label teenJooki = new Label("Valmistan Teie jooki...");
                teenJooki.setVisible(true);
                teenJooki.setLayoutX(195);
                teenJooki.setLayoutY(350);
                //teenJooki.setPrefSize(200, 25);
                teenJooki.setFont(Font.font("aldo", FontWeight.BOLD, 30));


                Label peaaeguValmis = new Label("Teie jook on peagi valmis...");
                peaaeguValmis.setVisible(false);
                peaaeguValmis.setLayoutX(145);
                peaaeguValmis.setLayoutY(450);
                //peaaeguValmis.setPrefSize(200, 25);
                peaaeguValmis.setFont(Font.font("aldo", FontWeight.BOLD, 30));

                Label valmis = new Label("Teie jook on valmis. \n" +
                        "  Kena päeva jätku!");
                valmis.setVisible(false);
                valmis.setLayoutX(195);
                valmis.setLayoutY(550);
                //valmis.setPrefSize(200, 25);
                valmis.setFont(Font.font("aldo", FontWeight.BOLD, 30));

                FadeTransition teen = new FadeTransition(Duration.seconds(2), teenJooki);
                teen.setFromValue(0.99);
                teen.setToValue(0.3);
                teen.setCycleCount(8);
                teen.setAutoReverse(true);

                teen.setOnFinished(event -> {
                    teenJooki.setVisible(false);
                    peaaeguValmis.setVisible(true);
                    FadeTransition peaaegu = new FadeTransition(Duration.seconds(1), peaaeguValmis);
                    peaaegu.setFromValue(0.99);
                    peaaegu.setToValue(0.3);
                    peaaegu.setCycleCount(10);
                    peaaegu.setAutoReverse(true);

                    peaaegu.setOnFinished(event1 -> {
                        try {
                            mängiMuusika("kell.wav");
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        } catch (FileNotFoundException ex){
                            System.out.println("Faili ei leitud");
                        } catch (IOException ex){
                            System.out.println("Mängimisel esines viga");
                        } catch (UnsupportedAudioFileException ex) {
                            ex.printStackTrace();
                        }
                        peaaeguValmis.setVisible(false);
                        valmis.setVisible(true);
                        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    kirjutLogi();
                                } catch (IOException ex) {
                                    System.out.println("Logi ei kirjutatud!");
                                }
                            }
                        }));
                        timeline1.setCycleCount(1);
                        timeline1.setOnFinished(actionEvent -> {
                            tagastus.setVisible(false);
                            teade2.setVisible(false);
                            teade3.setVisible(false);

                            maksa.setDisable(true);
                            MenüüsseMakse.setVisible(true);

                            kasutajaSisestatudSumma.clear();
                            kasutajaSisestatudSumma.setDisable(true);

                            ÜllatusLiikuv.setLayoutX(275);
                            ÜllatusLiikuv.setLayoutY(100);
                            ÜllatusVõlts.setVisible(true);
                            ÜllatusVõlts.setDisable(false);

                            medium.setDisable(false);
                            large.setDisable(false);
                            peaLava.setScene(Menüü1);

                        });
                        timeline1.play();

                    });
                    peaaegu.play();

                });
                teen.play();

                juur6.getChildren().addAll(teenJooki, peaaeguValmis, valmis);

            }else if(count == 0) {
                count++;
                teade3.setLayoutX(250);
                teade3.setLayoutY(610);
                teade3.setText("Sisestatud summa pole piisav."); // Kui kasutajasisend ei ole korras, siis väljastatakse vastav teade esimesel korral, teisel korral väljastatakse vastav teade veelkord ning kolmandal korral viiakse kasutaja menüüsse
            } else if (count == 1){
                count++;
                teade3.setLayoutX(230);
                teade3.setLayoutY(610);
                teade3.setText("Teil ei ole hetkel ikkagi piisavalt raha.");
            } else if(count>1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                teade2.setText("");
                teade3.setText("");
                kasutajaSisestatudSumma.clear();
                count = 0;
                maksa.setDisable(true);
                kasutajaSisestatudSumma.setDisable(true);
                medium.setDisable(false);
                large.setDisable(false);
                peaLava.setScene(Menüü1);
            }
        });

        juur5.getChildren().addAll(small, medium, large, maksa, teade2, teade3, tagastus, summaSisend, kasutajaSisestatudSumma, vaade, MenüüsseMakse);

        // Vaheleht 1 - lehel asub 6 jooki koos edasi-tagasi nuppudega
        Group juur2 = new Group(taust2);
        Menüü2 = new Scene(juur2, suurusX, suurusY);

        Button edasiVahelehelt_1 = new Button("");
        edasiVahelehelt_1.setOnMouseClicked(mouseEvent -> peaLava.setScene(Menüü3));
        edasiVahelehelt_1.setOnMouseEntered(MouseEvent -> {
            DropShadow vari1 = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            edasiVahelehelt_1.setEffect(vari1);
        });
        edasiVahelehelt_1.setOnMouseExited(mouseEvent -> {
            edasiVahelehelt_1.setEffect(null);
        });

        edasiVahelehelt_1.setLayoutX(640);
        edasiVahelehelt_1.setLayoutY(850);
        edasiVahelehelt_1.setMinSize(62, 52);
        edasiVahelehelt_1.setMaxSize(62, 52);

        Image iEdasiVl_2 = new Image("file:edasi.png", edasiVahelehelt_1.getWidth(), edasiVahelehelt_1.getHeight(), false, true, true);
        BackgroundImage bgiEdasiVl_2 = new BackgroundImage(iEdasiVl_2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(edasiVahelehelt_1.getWidth(), edasiVahelehelt_1.getHeight(), true, true, true, false));

        Background bgEdasiVl_2 = new Background(bgiEdasiVl_2);
        edasiVahelehelt_1.setBackground(bgEdasiVl_2);

        Button tagasiVahelehelt_1 = new Button("");
        tagasiVahelehelt_1.setOnMouseClicked(mouseEvent -> peaLava.setScene(Menüü1));
        tagasiVahelehelt_1.setOnMouseEntered(MouseEvent -> {
            DropShadow vari2 = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            tagasiVahelehelt_1.setEffect(vari2);
        });
        tagasiVahelehelt_1.setOnMouseExited(mouseEvent -> {
            tagasiVahelehelt_1.setEffect(null);
        });

        tagasiVahelehelt_1.setLayoutX(16);
        tagasiVahelehelt_1.setLayoutY(850);
        tagasiVahelehelt_1.setMinSize(62, 52);
        tagasiVahelehelt_1.setMaxSize(62, 52);

        Image iTagasi_M = new Image("file:tagasi.png", tagasiVahelehelt_1.getWidth(), tagasiVahelehelt_1.getHeight(), false, true, true);
        BackgroundImage bgiTagasi_M = new BackgroundImage(iTagasi_M, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(tagasiVahelehelt_1.getWidth(), tagasiVahelehelt_1.getHeight(), true, true, true, false));

        Background bgTagasi_M = new Background(bgiTagasi_M);
        tagasiVahelehelt_1.setBackground(bgTagasi_M);

        //jookide nupud
        Button mustKohv1 = new Button();
        mustKohv1.setMinSize(200, 200);
        mustKohv1.setMaxSize(200, 200);

        Image iMust = new Image("file:mustkohv.png", mustKohv1.getWidth(), mustKohv1.getHeight(), false, true, true);
        BackgroundImage bgiMust = new BackgroundImage(iMust, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(mustKohv1.getWidth(), mustKohv1.getHeight(), true, true, true, false));

        Background bgMust = new Background(bgiMust);
        mustKohv1.setBackground(bgMust);

        mustKohv1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 1;
            hind = mustKohv.getHind();
        });
        mustKohv1.setOnMouseEntered(MouseEvent -> {
            DropShadow variMK = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            mustKohv1.setEffect(variMK);
        });
        mustKohv1.setOnMouseExited(mouseEvent -> {
            mustKohv1.setEffect(null);
        });
        mustKohv1.setLayoutX(130);
        mustKohv1.setLayoutY(70);

        Button cappuccino1 = new Button();

        cappuccino1.setMinSize(200, 200);
        cappuccino1.setMaxSize(200, 200);

        Image iCappuccino = new Image("file:cappuccino.png", cappuccino1.getWidth(), cappuccino1.getHeight(), false, true, true);
        BackgroundImage bgiCappuccino = new BackgroundImage(iCappuccino, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(cappuccino1.getWidth(), cappuccino1.getHeight(), true, true, true, false));

        Background bgCappuccino = new Background(bgiCappuccino);
        cappuccino1.setBackground(bgCappuccino);

        cappuccino1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 2;
            hind = cappuccino.getHind();
        });
        cappuccino1.setOnMouseEntered(MouseEvent -> {
            DropShadow variCap = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            cappuccino1.setEffect(variCap);
        });
        cappuccino1.setOnMouseExited(mouseEvent -> {
            cappuccino1.setEffect(null);
        });
        cappuccino1.setLayoutX(130);
        cappuccino1.setLayoutY(320);

        Button caffeLatte1 = new Button();

        caffeLatte1.setMinSize(200, 200);
        caffeLatte1.setMaxSize(200, 200);

        Image iCaffe = new Image("file:caffelatte.png", caffeLatte1.getWidth(), caffeLatte1.getHeight(), false, true, true);
        BackgroundImage bgiCaffe = new BackgroundImage(iCaffe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(caffeLatte1.getWidth(), caffeLatte1.getHeight(), true, true, true, false));

        Background bgCaffe = new Background(bgiCaffe);
        caffeLatte1.setBackground(bgCaffe);

        caffeLatte1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 3;
            hind = caffeLatte.getHind();
        });
        caffeLatte1.setOnMouseEntered(MouseEvent -> {
            DropShadow variCl = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            caffeLatte1.setEffect(variCl);
        });
        caffeLatte1.setOnMouseExited(mouseEvent -> {
            caffeLatte1.setEffect(null);
        });
        caffeLatte1.setLayoutX(130);
        caffeLatte1.setLayoutY(570);

        Button espresso1 = new Button();

        espresso1.setMinSize(200, 200);
        espresso1.setMaxSize(200, 200);

        Image iEspresso = new Image("file:espresso.png", espresso1.getWidth(), espresso1.getHeight(), false, true, true);
        BackgroundImage bgiEspresso = new BackgroundImage(iEspresso, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(espresso1.getWidth(), espresso1.getHeight(), true, true, true, false));

        Background bgEspresso = new Background(bgiEspresso);
        espresso1.setBackground(bgEspresso);

        espresso1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 4;
            hind = espresso.getHind();
            medium.setDisable(true);
            large.setDisable(true);
        });
        espresso1.setOnMouseEntered(MouseEvent -> {
            DropShadow variEsp = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            espresso1.setEffect(variEsp);
        });
        espresso1.setOnMouseExited(mouseEvent -> {
            espresso1.setEffect(null);
        });
        espresso1.setLayoutX(410);
        espresso1.setLayoutY(70);

        Button flatWhite1 = new Button();

        flatWhite1.setMinSize(200, 200);
        flatWhite1.setMaxSize(200, 200);

        Image iFlat = new Image("file:flatwhite.png", flatWhite1.getWidth(), flatWhite1.getHeight(), false, true, true);
        BackgroundImage bgiFlat = new BackgroundImage(iFlat, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(flatWhite1.getWidth(), flatWhite1.getHeight(), true, true, true, false));

        Background bgFlat = new Background(bgiFlat);
        flatWhite1.setBackground(bgFlat);

        flatWhite1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 5;
            hind = flatWhite.getHind();
        });
        flatWhite1.setOnMouseEntered(MouseEvent -> {
            DropShadow variFw = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            flatWhite1.setEffect(variFw);
        });
        flatWhite1.setOnMouseExited(mouseEvent -> {
            flatWhite1.setEffect(null);
        });
        flatWhite1.setLayoutX(410);
        flatWhite1.setLayoutY(320);

        Button longBlack1 = new Button();

        longBlack1.setMinSize(200, 200);
        longBlack1.setMaxSize(200, 200);

        Image iLong = new Image("file:longblack.png", longBlack1.getWidth(), longBlack1.getHeight(), false, true, true);
        BackgroundImage bgiLong = new BackgroundImage(iLong, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(longBlack1.getWidth(), longBlack1.getHeight(), true, true, true, false));

        Background bgLong = new Background(bgiLong);
        longBlack1.setBackground(bgLong);

        longBlack1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 6;
            hind = longBlack.getHind();
        });
        longBlack1.setOnMouseEntered(MouseEvent -> {
            DropShadow variLb = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            longBlack1.setEffect(variLb);
        });
        longBlack1.setOnMouseExited(mouseEvent -> {
            longBlack1.setEffect(null);
        });
        longBlack1.setLayoutX(410);
        longBlack1.setLayoutY(570);

        juur2.getChildren().addAll(edasiVahelehelt_1, tagasiVahelehelt_1, mustKohv1, espresso1, caffeLatte1, cappuccino1, longBlack1, flatWhite1);

        // Vaheleht 2 - lehel asub 6 jooki koos edasi-tagasi nuppudega
        Group juur3= new Group(taust3);

        Menüü3 = new Scene(juur3, suurusX, suurusY);

        Button edasiVahelehelt_2 = new Button("");
        edasiVahelehelt_2.setOnMouseClicked(mouseEvent -> peaLava.setScene(Menüü4));
        edasiVahelehelt_2.setOnMouseEntered(MouseEvent -> {
            DropShadow vari3 = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            edasiVahelehelt_2.setEffect(vari3);
        });
        edasiVahelehelt_2.setOnMouseExited(mouseEvent -> {
            edasiVahelehelt_2.setEffect(null);
        });

        edasiVahelehelt_2.setLayoutX(640);
        edasiVahelehelt_2.setLayoutY(850);
        edasiVahelehelt_2.setMinSize(62, 52);
        edasiVahelehelt_2.setMaxSize(62, 52);

        Image iEdasi_Ü = new Image("file:edasi.png", edasiVahelehelt_2.getWidth(), edasiVahelehelt_2.getHeight(), false, true, true);
        BackgroundImage bgiEdasi_Ü = new BackgroundImage(iEdasi_Ü, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(edasiVahelehelt_2.getWidth(), edasiVahelehelt_2.getHeight(), true, true, true, false));

        Background bgEdasi_Ü = new Background(bgiEdasi_Ü);
        edasiVahelehelt_2.setBackground(bgEdasi_Ü);

        Button tagasiVahelehelt_2 = new Button("");
        tagasiVahelehelt_2.setOnMouseClicked(mouseEvent -> peaLava.setScene(Menüü2));
        tagasiVahelehelt_2.setOnMouseEntered(MouseEvent -> {
            DropShadow vari4 = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            tagasiVahelehelt_2.setEffect(vari4);
        });
        tagasiVahelehelt_2.setOnMouseExited(mouseEvent -> {
            tagasiVahelehelt_2.setEffect(null);
        });
        tagasiVahelehelt_2.setLayoutX(16);
        tagasiVahelehelt_2.setLayoutY(850);
        tagasiVahelehelt_2.setMinSize(62, 52);
        tagasiVahelehelt_2.setMaxSize(62, 52);

        Image iTagasiVl_1 = new Image("file:tagasi.png", tagasiVahelehelt_2.getWidth(), tagasiVahelehelt_2.getHeight(), false, true, true);
        BackgroundImage bgiTagasiVl_1 = new BackgroundImage(iTagasiVl_1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(tagasiVahelehelt_2.getWidth(), tagasiVahelehelt_2.getHeight(), true, true, true, false));

        Background bgTagasiVl_1 = new Background(bgiTagasiVl_1);
        tagasiVahelehelt_2.setBackground(bgTagasiVl_1);

        //jookide nupud
        Button LatteArt1 = new Button();

        LatteArt1.setMinSize(200, 200);
        LatteArt1.setMaxSize(200, 200);

        Image iArt = new Image("file:latteart.png", LatteArt1.getWidth(), LatteArt1.getHeight(), false, true, true);
        BackgroundImage bgiArt = new BackgroundImage(iArt, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(LatteArt1.getWidth(), LatteArt1.getHeight(), true, true, true, false));

        Background bgArt = new Background(bgiArt);
        LatteArt1.setBackground(bgArt);

        LatteArt1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 7;
            hind = latteArt.getHind();
        });
        LatteArt1.setOnMouseEntered(MouseEvent -> {
            DropShadow variLa = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            LatteArt1.setEffect(variLa);
        });
        LatteArt1.setOnMouseExited(mouseEvent -> {
            LatteArt1.setEffect(null);
        });
        LatteArt1.setLayoutX(130);
        LatteArt1.setLayoutY(570);

        Button CafeauLait1 = new Button();

        CafeauLait1.setMinSize(200, 200);
        CafeauLait1.setMaxSize(200, 200);

        Image iCafe = new Image("file:cafeaulait.png", CafeauLait1.getWidth(), CafeauLait1.getHeight(), false, true, true);
        BackgroundImage bgiCafe = new BackgroundImage(iCafe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(CafeauLait1.getWidth(), CafeauLait1.getHeight(), true, true, true, false));

        Background bgCafe = new Background(bgiCafe);
        CafeauLait1.setBackground(bgCafe);

        CafeauLait1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 8;
            hind = cafeAuLait.getHind();
        });
        CafeauLait1.setOnMouseEntered(MouseEvent -> {
            DropShadow variCaL = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            CafeauLait1.setEffect(variCaL);
        });
        CafeauLait1.setOnMouseExited(mouseEvent -> {
            CafeauLait1.setEffect(null);
        });
        CafeauLait1.setLayoutX(410);
        CafeauLait1.setLayoutY(320);

        Button Affogato1 = new Button();

        Affogato1.setMinSize(200, 200);
        Affogato1.setMaxSize(200, 200);

        Image iAffo = new Image("file:affogato.png", Affogato1.getWidth(), Affogato1.getHeight(), false, true, true);
        BackgroundImage bgiAffo = new BackgroundImage(iAffo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(Affogato1.getWidth(), Affogato1.getHeight(), true, true, true, false));

        Background bgAffo = new Background(bgiAffo);
        Affogato1.setBackground(bgAffo);

        Affogato1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 9;
            hind = affogato.getHind();
        });
        Affogato1.setOnMouseEntered(MouseEvent -> {
            DropShadow variAg = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            Affogato1.setEffect(variAg);
        });
        Affogato1.setOnMouseExited(mouseEvent -> {
            Affogato1.setEffect(null);
        });
        Affogato1.setLayoutX(130);
        Affogato1.setLayoutY(70);

        Button Chailatte1 = new Button();

        Chailatte1.setMinSize(200, 200);
        Chailatte1.setMaxSize(200, 200);

        Image iChai = new Image("file:chailatte.png", Chailatte1.getWidth(), Chailatte1.getHeight(), false, true, true);
        BackgroundImage bgiChai = new BackgroundImage(iChai, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(Chailatte1.getWidth(), Chailatte1.getHeight(), true, true, true, false));

        Background bgChai = new Background(bgiChai);
        Chailatte1.setBackground(bgChai);

        Chailatte1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 10;
            hind = chaiLatte.getHind();
        });
        Chailatte1.setOnMouseEntered(MouseEvent -> {
            DropShadow variChL = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            Chailatte1.setEffect(variChL);
        });
        Chailatte1.setOnMouseExited(mouseEvent -> {
            Chailatte1.setEffect(null);
        });
        Chailatte1.setLayoutX(410);
        Chailatte1.setLayoutY(570);

        Button Mocha1 = new Button();

        Mocha1.setMinSize(200, 200);
        Mocha1.setMaxSize(200, 200);

        Image iMocha = new Image("file:mocha.png", Chailatte1.getWidth(), Chailatte1.getHeight(), false, true, true);
        BackgroundImage bgiMocha = new BackgroundImage(iMocha, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(Chailatte1.getWidth(), Chailatte1.getHeight(), true, true, true, false));

        Background bgMocha = new Background(bgiMocha);
        Mocha1.setBackground(bgMocha);

        Mocha1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 11;
            hind = mocha.getHind();
        });
        Mocha1.setOnMouseEntered(MouseEvent -> {
            DropShadow variMoc = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            Mocha1.setEffect(variMoc);
        });
        Mocha1.setOnMouseExited(mouseEvent -> {
            Mocha1.setEffect(null);
        });
        Mocha1.setLayoutX(410);
        Mocha1.setLayoutY(70);

        Button Kakao1 = new Button();

        Kakao1.setMinSize(200, 200);
        Kakao1.setMaxSize(200, 200);

        Image iKakao = new Image("file:kakao.png", Chailatte1.getWidth(), Chailatte1.getHeight(), false, true, true);
        BackgroundImage bgiKakao = new BackgroundImage(iKakao, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(Chailatte1.getWidth(), Chailatte1.getHeight(), true, true, true, false));

        Background bgKakao = new Background(bgiKakao);
        Kakao1.setBackground(bgKakao);

        Kakao1.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(SuurusJaMakse);
            number = 12;
            hind = kakao.getHind();
        });
        Kakao1.setOnMouseEntered(MouseEvent -> {
            DropShadow variKao = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            Kakao1.setEffect(variKao);
        });
        Kakao1.setOnMouseExited(mouseEvent -> {
            Kakao1.setEffect(null);
        });
        Kakao1.setLayoutX(130);
        Kakao1.setLayoutY(320);

        juur3.getChildren().addAll(edasiVahelehelt_2, tagasiVahelehelt_2, Mocha1, Affogato1, CafeauLait1, Chailatte1, LatteArt1, Kakao1);

        // Üllatus - kasutajal on võimalik valida, kas ta soovib üllatus või mitte, samuti on võimalik tagasi menüüsse pöörduda
        Group juur7 = new Group(taust7);
        Üllatusleht = new Scene(juur7, suurusX, suurusY);

        Label valik = new Label(
                "                                Te valisite Üllatuse!?" +
                        "\n\n                Üllatuse saamiseks tuleb maksta 2.30€." + "\n\n                    Kas olete kindel, et julgete jätkata?");
        valik.setLayoutX(155);
        valik.setLayoutY(275);
        valik.setFont(Font.font("aldo", FontWeight.BOLD, 16));

        Label EiLeht = new Label("                            Teie Valik. Kena päeva jätku!" +
                "\n\n            Uue joogi valimiseks suunduge tagasi menüüsse.");
        EiLeht.setLayoutX(155);
        EiLeht.setLayoutY(275);
        EiLeht.setVisible(false);
        EiLeht.setFont(Font.font("aldo", FontWeight.BOLD, 16));

        Button Jah = new Button();
        Jah.setLayoutX(275);
        Jah.setLayoutY(450);
        Jah.setMinSize(50, 50);
        Jah.setMaxSize(50, 50);

        Image JahIm = new Image("file:jah.png", Jah.getWidth(), Jah.getHeight(), false, true, true);
        BackgroundImage bgiJah = new BackgroundImage(JahIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(edasiVahelehed.getWidth(), edasiVahelehed.getHeight(), true, true, true, false));
        Background bgJahl = new Background(bgiJah);
        Jah.setBackground(bgJahl);

        Jah.setOnMouseEntered(MouseEvent -> {
            DropShadow vari7 = new DropShadow(BlurType.THREE_PASS_BOX, Color.GREEN, 15,  0,  0, 0);
            Jah.setEffect(vari7);
        });
        Jah.setOnMouseExited(MouseEvent -> {
            Jah.setEffect(null);
        });

        Jah.setOnMouseClicked(MouseEvent -> {
            //Üllatusmakse leht - toimub üllatuse eest tasumine
            Group juur8 = new Group(ivMakseÜllatus);
            Üllatusmakse = new Scene(juur8, suurusX, suurusY);

            Button maksaÜllatus = new Button();
            maksaÜllatus.setLayoutX(510);
            maksaÜllatus.setLayoutY(543);
            maksaÜllatus.setPrefSize(90, 40);
            maksaÜllatus.setDisable(true);

            Image imaksaÜllatus = new Image("file:maksa.png", maksaÜllatus.getWidth(), maksaÜllatus.getHeight(), false, true, true);
            BackgroundImage bgimaksaÜllatus = new BackgroundImage(imaksaÜllatus, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(maksaÜllatus.getWidth(), maksaÜllatus.getHeight(), true, true, true, false));

            Background bgMenüüsseÜllatusest = new Background(bgimaksaÜllatus);
            maksaÜllatus.setBackground(bgMenüüsseÜllatusest);

            maksaÜllatus.setOnMouseEntered(mouseEvent -> {
                DropShadow variÜl1 = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
                maksaÜllatus.setEffect(variÜl1);
            });
            maksaÜllatus.setOnMouseExited(mouseEvent -> {
                maksaÜllatus.setEffect(null);
            });

            TextField kasutajaSummaÜllatus = new TextField();
            kasutajaSummaÜllatus.setLayoutX(235);
            kasutajaSummaÜllatus.setLayoutY(551);
            kasutajaSummaÜllatus.setPrefSize(250,25);

            Label summaSisendÜllatus = new Label("Tasuge valitud joogi eest:");
            summaSisendÜllatus.setLayoutX(30);
            summaSisendÜllatus.setLayoutY(550);
            summaSisendÜllatus.setPrefSize(200,25);
            summaSisendÜllatus.setFont(Font.font("aldo", FontWeight.BOLD, 16));

            Label teade1 = new Label("Teie valitud jook maksab 2.3 eurot.");
            teade1.setLayoutX(230);
            teade1.setLayoutY(495);
            teade1.setPrefSize(300,25);
            teade1.setFont(Font.font("aldo", FontWeight.BOLD, 16));

            Label teade4 = new Label("");
            teade4.setLayoutX(315);
            teade4.setLayoutY(580);
            teade4.setPrefSize(250,25);
            teade4.setFont(Font.font("aldo", FontWeight.BOLD, 16));

            Label teade5 = new Label("");
            teade5.setLayoutX(270);
            teade5.setLayoutY(590);
            teade5.setPrefSize(300,25);
            teade5.setFont(Font.font("aldo", FontWeight.BOLD, 16));

            Label tagastusÜllatus = new Label("");
            tagastusÜllatus.setLayoutX(290);
            tagastusÜllatus.setLayoutY(620);
            tagastusÜllatus.setPrefSize(200,25);
            tagastusÜllatus.setFont(Font.font("aldo", FontWeight.BOLD, 16));

            // Nupp viib tagasi menüüsse ja lähtestab muutujad
            Button MenüüsseÜllatuseMakse = new Button();
            MenüüsseÜllatuseMakse.setOnMouseClicked(mouseEvent1 -> {
                peaLava.setScene(Menüü1);
                teade4.setText("");
                teade5.setText("");
                kasutajaSummaÜllatus.clear();
                count = 0;
            });
            MenüüsseÜllatuseMakse.setLayoutX(16);
            MenüüsseÜllatuseMakse.setLayoutY(850);
            MenüüsseÜllatuseMakse.setPrefSize(73, 73);

            Image iMenüüsseÜllatuseMakse = new Image("file:menu.png", MenüüsseÜllatuseMakse.getWidth(), MenüüsseÜllatuseMakse.getHeight(), false, true, true);
            BackgroundImage bgiMenüüsseÜllatuseMakse = new BackgroundImage(iMenüüsseÜllatuseMakse, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(MenüüsseÜllatuseMakse.getWidth(), MenüüsseÜllatuseMakse.getHeight(), true, true, true, false));

            Background bgMenüüsseÜllatuseMakse = new Background(bgiMenüüsseÜllatuseMakse);
            MenüüsseÜllatuseMakse.setBackground(bgMenüüsseÜllatuseMakse);

            MenüüsseÜllatuseMakse.setOnMouseEntered(mouseEvent2 -> {
                DropShadow borderGlow= new DropShadow();
                borderGlow.setOffsetY(0f);
                borderGlow.setOffsetX(0f);
                borderGlow.setColor(Color.BLACK);
                MenüüsseÜllatuseMakse.setEffect(borderGlow);
            });
            MenüüsseÜllatuseMakse.setOnMouseExited(mouseEvent2 -> MenüüsseÜllatuseMakse.setEffect(null));

            tekstiKontroll(maksaÜllatus, kasutajaSummaÜllatus, "(\\d+.)|(\\d+(\\.\\d+)?)");

            maksaÜllatus.setOnAction(e -> {
                double sisestatudSummaÜllatus = Double.parseDouble(kasutajaSummaÜllatus.getText());
                if (sisestatudSummaÜllatus >= 2.30){
                    MenüüsseÜllatuseMakse.setVisible(false);
                    teade5.setText("");
                    teade4.setText("Makse edukas!");
                    if (sisestatudSummaÜllatus > 2.30) { // kui kliendi sisestatud raha on > kui joogi lõplik hind
                        double tagasi = 0.0;
                        tagasi = Math.round((sisestatudSummaÜllatus - 2.30) * 100.0) / 100.0;
                        tagastusÜllatus.setText("Saate tagasi " + tagasi + " eurot.");
                        maksaÜllatus.setDisable(true);
                        kasutajaSummaÜllatus.setDisable(true);
                    }
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            //sleepi eest
                        }
                    }));
                    timeline.setCycleCount(1);
                    timeline.play();
                    timeline.setOnFinished(actionEvent -> {
                        //ÜllatusValmis - lehel kuvatakse kasutajale erinevaid teateid ning peale joogi nii-öelda väljastamist pöördutakse tagasi menüüsse
                        Group juur9 = new Group(taust8);
                        ÜllatusValmis = new Scene(juur9, suurusX, suurusY);

                        Label teenJooki = new Label("Valmistan Teie jooki...");
                        teenJooki.setVisible(true);
                        teenJooki.setLayoutX(195);
                        teenJooki.setLayoutY(350);
                        teenJooki.setFont(Font.font("aldo", FontWeight.BOLD, 30));


                        Label peaaeguValmis = new Label("Teie jook on peagi valmis...");
                        peaaeguValmis.setVisible(false);
                        peaaeguValmis.setLayoutX(145);
                        peaaeguValmis.setLayoutY(450);
                        peaaeguValmis.setFont(Font.font("aldo", FontWeight.BOLD, 30));

                        Label valmis = new Label("");
                        valmis.setVisible(false);
                        valmis.setLayoutX(200);
                        valmis.setLayoutY(450);
                        valmis.setFont(Font.font("aldo", FontWeight.BOLD, 30));

                        try {
                            mängiMuusika("Masin.wav");
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (UnsupportedAudioFileException ex) {
                            ex.printStackTrace();
                        }

                        FadeTransition teen = new FadeTransition(Duration.seconds(2), teenJooki);
                        teen.setFromValue(0.99);
                        teen.setToValue(0.3);
                        teen.setCycleCount(8);
                        teen.setAutoReverse(true);

                        teen.setOnFinished(event -> { //kohvijoogi tegemise hääl
                            teenJooki.setVisible(false);
                            peaaeguValmis.setVisible(true);
                            FadeTransition peaaegu = new FadeTransition(Duration.seconds(1), peaaeguValmis);
                            peaaegu.setFromValue(0.99);
                            peaaegu.setToValue(0.3);
                            peaaegu.setCycleCount(3);
                            peaaegu.setAutoReverse(true);

                            peaaegu.setOnFinished(event1 -> {
                                peaaeguValmis.setVisible(false);

                                //suvalise joogi valimine
                                Random rand1 = new Random();
                                int index = rand1.nextInt(kohvijoogid.size());
                                JoogidjaHinnad jook = kohvijoogid.get(index);
                                String jookNimi = jook.getKohviJook(); //isendiväljast suvalise kohvijoogi saamine
                                number = jook.getJärjekorranumber();
                                suurusTops = "s";

                                if (jookNimi.equals("Keeduvesi")) {
                                    try {
                                        mängiMuusika("AstronomiaMeme.wav");
                                    } catch (LineUnavailableException ex) {
                                        ex.printStackTrace();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    } catch (UnsupportedAudioFileException ex) {
                                        ex.printStackTrace();
                                    }
                                    Timeline tlAstro = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {

                                        }
                                    }));
                                    tlAstro.play();
                                    tlAstro.setOnFinished(actionEvent1 -> {
                                        valmis.setVisible(true);
                                        valmis.setLayoutX(100);
                                        valmis.setLayoutY(520);
                                        valmis.setText("            Võitsite Keeduvett! \n" +
                                                "Kohvijoogi saamiseks võite alati \n" +
                                                "  meie masinat uuesti kasutada!\n" +
                                                "            Ilusat päeva Teile!");
                                    });

                                } else {
                                    try {
                                        mängiMuusika("ElMexican.wav");
                                    } catch (LineUnavailableException ex) {
                                        ex.printStackTrace();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    } catch (UnsupportedAudioFileException ex) {
                                        ex.printStackTrace();
                                    }
                                    Timeline tlElMex = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent actionEvent) {

                                        }
                                    }));
                                    tlElMex.play();
                                    tlElMex.setOnFinished(actionEvent1 -> {
                                        valmis.setVisible(true);
                                        valmis.setLayoutX(85);
                                        valmis.setLayoutY(520);
                                        valmis.setText("                     Palju õnne! \n" +
                                                "Võitsite (väikese) joogi " + jookNimi + "!\n" +
                                                "               Ilusat päeva Teile!");
                                    });
                                }

                                //ajaviide ja logi kirjutamine
                                Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(19), new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        try {
                                            kirjutLogi();
                                        } catch (IOException ex) {
                                            System.out.println("Logi ei kirjutatud!");
                                        }
                                    }
                                }));
                                timeline1.play();
                                timeline1.setOnFinished(actionEvent1 -> {
                                    tagastus.setVisible(false);
                                    teade2.setVisible(false);
                                    teade3.setVisible(false);

                                    maksa.setDisable(true);
                                    MenüüsseMakse.setVisible(true);

                                    kasutajaSisestatudSumma.clear();
                                    kasutajaSisestatudSumma.setDisable(true);

                                    medium.setDisable(false);
                                    large.setDisable(false);
                                    ÜllatusLiikuv.setLayoutX(275);
                                    ÜllatusLiikuv.setLayoutY(100);
                                    ÜllatusVõlts.setVisible(true);
                                    ÜllatusVõlts.setDisable(false);
                                    peaLava.setScene(Menüü1);
                                });

                            });
                            peaaegu.play();

                        });
                        teen.play();

                        juur9.getChildren().addAll(teenJooki, peaaeguValmis, valmis);

                        peaLava.setScene(ÜllatusValmis);
                        try {
                            mängiMuusika("Masin.wav");
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (UnsupportedAudioFileException ex) {
                            ex.printStackTrace();
                        }

                    });


                }else if(count == 0) {
                    count++;
                    teade5.setLayoutX(250);
                    teade5.setLayoutY(610);
                    teade5.setText("Sisestatud summa pole piisav.");
                } else if (count == 1){
                    count++;
                    teade5.setLayoutX(230);
                    teade5.setLayoutY(610);
                    teade5.setText("Teil ei ole hetkel ikkagi piisavalt raha.");
                } else if(count>1){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    peaLava.setScene(Menüü1);
                    teade4.setText("");
                    teade5.setText("");
                    kasutajaSummaÜllatus.clear();
                    count = 0;
                    maksaÜllatus.setDisable(true);
                }
            });


            juur8.getChildren().addAll(maksaÜllatus, kasutajaSummaÜllatus, summaSisendÜllatus, teade1, teade4, teade5, tagastusÜllatus, MenüüsseÜllatuseMakse);

            peaLava.setScene(Üllatusmakse);
        });

        Button Ei = new Button();
        Ei.setLayoutX(420);
        Ei.setLayoutY(450);
        Ei.setMinSize(50, 50);
        Ei.setMaxSize(50, 50);

        Image EiIm = new Image("file:ei.png", Ei.getWidth(), Ei.getHeight(), false, true, true);
        BackgroundImage bgiEi = new BackgroundImage(EiIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(edasiVahelehed.getWidth(), edasiVahelehed.getHeight(), true, true, true, false));
        Background bgEi = new Background(bgiEi);
        Ei.setBackground(bgEi);

        Ei.setOnMouseEntered(MouseEvent -> {
            DropShadow variEi = new DropShadow(BlurType.THREE_PASS_BOX, Color.RED, 15,  0,  0, 0);
            Ei.setEffect(variEi);
        });
        Ei.setOnMouseExited(mouseEvent -> {
            Ei.setEffect(null);
        });

        Ei.setOnMouseClicked(mouseEvent -> {
            EiLeht.setVisible(true);
            valik.setVisible(false);
            Ei.setVisible(false);
            Jah.setVisible(false);
        });

        // Nupp viib tagasi menüüsse ja lähtestab muutujad
        Button MenüüsseÜllatusest = new Button("");

        MenüüsseÜllatusest.setMinSize(73, 73);
        MenüüsseÜllatusest.setMaxSize(73, 73);

        Image iMenüüsseÜllatusest = new Image("file:menu.png", MenüüsseÜllatusest.getWidth(), MenüüsseÜllatusest.getHeight(), false, true, true);
        BackgroundImage bgiMenüüsseÜllatusest = new BackgroundImage(iMenüüsseÜllatusest, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(MenüüsseÜllatusest.getWidth(), MenüüsseÜllatusest.getHeight(), true, true, true, false));

        Background bgMenüüsseÜllatusest = new Background(bgiMenüüsseÜllatusest);
        MenüüsseÜllatusest.setBackground(bgMenüüsseÜllatusest);

        MenüüsseÜllatusest.setOnMouseClicked(mouseEvent -> {
            peaLava.setScene(Menüü1);
            EiLeht.setVisible(false);
            valik.setVisible(true);
            Ei.setVisible(true);
            Jah.setVisible(true);
        });
        MenüüsseÜllatusest.setOnMouseEntered(mouseEvent -> {
            DropShadow variMenuÜll = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 25,  0,  0, 0);
            MenüüsseÜllatusest.setEffect(variMenuÜll);
        });
        MenüüsseÜllatusest.setOnMouseExited(mouseEvent -> MenüüsseÜllatusest.setEffect(null));
        MenüüsseÜllatusest.setLayoutX(16);
        MenüüsseÜllatusest.setLayoutY(840);

        juur7.getChildren().addAll(valik, Jah, Ei, MenüüsseÜllatusest, EiLeht);

    }

    public static void main(String[] args) {
        launch(args);
    }
}

