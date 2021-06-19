/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodNumber;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrassi"
    private Button btnGrassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	if(!isValidGrafo()) {
    		return;
    	}
    	
    	String s = this.txtPorzioni.getText();
    	String result = model.creaGrafo(Integer.parseInt(s));
    	
    	txtResult.appendText(result);
    	
    	this.btnGrassi.setDisable(false);
    	this.btnSimula.setDisable(false);
    	this.txtK.setDisable(false);
    	this.boxFood.setDisable(false);
    	this.boxFood.getItems().setAll(model.getVertici());
    }

    private boolean isValidGrafo() {
		String input = this.txtPorzioni.getText();
		if(input.equals("")) {
			txtResult.appendText("ERRORE: inserire un valore per le prozioni");
			return false;
		}
		try {
			if(Integer.parseInt(input) < 0) {
				txtResult.appendText("ERRORE: le porzioni devono essere un numero intero e positivo");
				return false;
			}
		}catch(NumberFormatException nfe) {
			txtResult.appendText("ERRORE: le porzioni devono essere un numero intero");
			return false;
		}
		return true;
	}

	@FXML
    void doGrassi(ActionEvent event) {
    	txtResult.clear();
    	Food f = this.boxFood.getValue();
    	if(f == null) {
    		txtResult.appendText("ERRORE: selezionare un cibo");
    		return;
    	}
    	
    	List<FoodNumber> result = model.getAdiacentiMinimi(f);
    	if(result.isEmpty()) {
    		txtResult.appendText("Non vi sono archi adiacenti a " + f);
    	}else {
    		txtResult.appendText("I " + result.size() + " cibi adiacenti a " + f + "con la minore differenza di grassi sono:\n" );
    		for(FoodNumber fn: result) {
    			txtResult.appendText("\n" + fn.toString());
    		}
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	if(!isValid()) {
    		return;
    	}
    	
    	String k = this.txtK.getText();
    	Food f = this.boxFood.getValue();
    	
    	model.init(Integer.parseInt(k), f);
    	model.run();
    	
    	txtResult.appendText("Simulazione della cucina con " + k + " stazioni partendo dal cibo " + f);
    	txtResult.appendText("\n\nTEMPO TOTALE = " + model.getTempo() + "ore");
    	txtResult.appendText("\n\n# PREPARAZIONI TOTALI = " + model.getNumPreparati());
    }

    private boolean isValid() {
		String input = this.txtK.getText();
		boolean check = true;
		if(this.boxFood.getValue() == null) {
			txtResult.appendText("ERRORE: scegliere un cibo per la simulazione\n");
			check = false;
		}
		if(input.equals("")) {
			txtResult.appendText("ERRORE: inserire un valore per le cucine\n");
			check = false;
		}else {
			try {
				int num = Integer.parseInt(input);
				if( num < 1 || num > 10 ) {
					txtResult.appendText("ERRORE: le porzioni devono essere un numero intero e positivo tra 1 e 10");
					check = false;
				}
			}catch(NumberFormatException nfe) {
				txtResult.appendText("ERRORE: le porzioni devono essere un numero intero");
				check = false;
			}			
		}

		
		return check;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
