package com.pack.controllers;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@Data
public class AppService {
    private AppState state;

    public void setStateModel(AppState state, Model model) {
        this.state = state;
        model.addAttribute("appState", state.toString().toLowerCase());
        model.addAttribute("can_write", true);
    }
}
