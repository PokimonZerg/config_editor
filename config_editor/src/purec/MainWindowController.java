package purec;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import java.net.URL;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import com.sun.javafx.robot.*;

import javafx.application.Platform;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javafx.scene.paint.Color;

import javafx.util.Callback;

/**
 * Controls all components and events
 */
public class MainWindowController implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        profileManager = new purec.ProfileManager();
        
        user_combobox.getItems().clear();
        user_combobox.getItems().addAll(profileManager.getProfiles());
        
        resolution_combobox.getItems().clear();
        resolution_combobox.getItems().addAll(generateResolutionStrings());
        
        showProfile(profileManager.getCurrentProfile());
        
        Platform.runLater(new Runnable() { public void run() { new_button.requestFocus(); } });
        
        addCustomListeners();
    }
    
    /**
     * Generate array with all avalaible resolutions.
     * @return array of resolution strings
     */
    private ResolutionString[] generateResolutionStrings()
    {
        GraphicsEnvironment g                 = GraphicsEnvironment.getLocalGraphicsEnvironment();
        DisplayMode[] display_mods            = g.getDefaultScreenDevice().getDisplayModes();
        ResolutionString[] resolution_strings = new ResolutionString[display_mods.length];
        
        for(int i = 0; i < display_mods.length; i++)
        {
            resolution_strings[i] = new ResolutionString(display_mods[i]);
        }
        
        return resolution_strings;
    }
    
    /**
     * Display profile on window.
     * @param p profile reference
     */
    private void showProfile(Profile p)
    {
        fullscreen_checkbox.setSelected(p.isFullscreen());
        vsync_checkbox.setSelected(p.isVsync());
        sound_checkbox.setSelected(p.isSound());
        music_checkbox.setSelected(p.isMusic());
        
        sound_slider.setValue((double)p.getSoundVolume());
        music_slider.setValue((double)p.getMusicVolume());
        
        sizex_field.setText(String.valueOf(p.getSizex()));
        sizey_field.setText(String.valueOf(p.getSizey()));
        
        user_combobox.setValue(p);
        resolution_combobox.setValue(findResolutionString(p.getResx(), p.getResy()));
    }
    
    /**
     * Find resolution string item in resolution combo box.
     * @param x resolution width
     * @param y resolution height
     * @return ResolutionString class reference or null
     */
    private ResolutionString findResolutionString(int x, int y)
    {
        for(ResolutionString res: resolution_combobox.getItems())
        {
            if(res.compare(x, y))
                return res;
        }
        
        return null;
    }
    
    /**
     * Cancel button click handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void cancelButtonClick(ActionEvent event)
    {
        System.exit(0);
    }
    
    /**
     * Default button event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void defaultButtonClick(ActionEvent event)
    {
        profileManager.getCurrentProfile().setToDefault();
        
        showProfile(profileManager.getCurrentProfile());
    }
    
    /**
     * Save button event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void saveButtonClick(ActionEvent event)
    {
        profileManager.saveProfile(profileManager.getCurrentProfile());
    }
    
    /**
     * New button event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void newButtonClick(ActionEvent event)
    {
        profileManager.setCurrentProfile(profileManager.createNewProfile());
        
        user_combobox.getItems().add(profileManager.getCurrentProfile()); 

        showProfile(profileManager.getCurrentProfile());
    }
    
    /**
     * Delete button event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void deleteButtonClick(ActionEvent event)
    {
        profileManager.deleteProfile(profileManager.getCurrentProfile());
        
        showProfile(profileManager.getCurrentProfile());
    }
    
    /**
     * User combobox event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void userComboBoxSelect(ActionEvent event)
    {
        Object p = user_combobox.getValue();
        
        if(p instanceof Profile)
        {
            profileManager.setCurrentProfile((Profile)p);
        
            showProfile(profileManager.getCurrentProfile());
        }
    }
    
    /**
     * Resolution combobox event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void resolutionComboBoxSelect(ActionEvent event)
    {
        profileManager.getCurrentProfile().setResx(resolution_combobox.getValue().getWidth());
        profileManager.getCurrentProfile().setResy(resolution_combobox.getValue().getHeight());
    }
    
    /**
     * Fullscreen checkbox event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void fullscreenCheckBoxSelect(ActionEvent event)
    {
        profileManager.getCurrentProfile().setFullscreen(fullscreen_checkbox.isSelected());
    }
    
    /**
     * Vsync checkbox event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void vsyncCheckBoxSelect(ActionEvent event)
    {
        profileManager.getCurrentProfile().setVsync(vsync_checkbox.isSelected());
    }
    
    /**
     * Sound checkbox event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void soundCheckBoxSelect(ActionEvent event)
    {
        profileManager.getCurrentProfile().setSound(sound_checkbox.isSelected());
    }
    
    /**
     * Music checkbox event handler.
     * @param event {@link javafx.event.ActionEvent}
     */
    @FXML
    private void musicCheckBoxSelect(ActionEvent event)
    {
        profileManager.getCurrentProfile().setMusic(music_checkbox.isSelected());
    }
    
    /**
     * Add custom listeners for sliders and text fields.
     */
    private void addCustomListeners()
    {
        user_combobox.showingProperty().addListener(
            new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean old_bool, Boolean new_bool)
                {
                    if(new_bool)
                    {
                        user_combobox.getItems().clear();
                        user_combobox.getItems().addAll(profileManager.getProfiles());
                    }
                }
            });
        
        user_combobox.getEditor().textProperty().addListener(
            new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> ov, String old_string, String new_string)
                {
                    if(!user_combobox.isShowing()) // HACK: combobox false event check.
                    {
                        profileManager.getCurrentProfile().setUser(new_string);
                    }
                }
            });
        
        sound_slider.valueProperty().addListener(
            new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
                {
                    profileManager.getCurrentProfile().setSoundVolume(new_val.intValue());
                }
            });
        
        music_slider.valueProperty().addListener(
            new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
                {
                    profileManager.getCurrentProfile().setMusicVolume(new_val.intValue());
                }
            });
        
        sizex_field.textProperty().addListener(
            new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> ov, String old_val, String new_val)
                {
                    profileManager.getCurrentProfile().setSizex(Integer.valueOf(new_val));
                }
            });
        
        sizey_field.textProperty().addListener(
            new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> ovalue, String old_val, String new_val)
                {
                    profileManager.getCurrentProfile().setSizey(Integer.valueOf(new_val));
                }
            });
    }
    
    private ProfileManager profileManager;
    
    @FXML
    private Button cancel_button, new_button, delete_button, default_button, save_button;
    @FXML
    private CheckBox fullscreen_checkbox, vsync_checkbox, sound_checkbox, music_checkbox;
    @FXML
    private Slider sound_slider, music_slider;
    @FXML
    private TextField sizex_field, sizey_field;
    @FXML
    private ComboBox<Profile> user_combobox;
    @FXML
    private ComboBox<ResolutionString> resolution_combobox;
}
