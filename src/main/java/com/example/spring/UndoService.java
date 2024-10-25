package com.example.spring;


import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class UndoService {
    private Stack<Action> undo = new Stack<>();
    private Stack<Action> redo = new Stack<>();

    public void recordAction(Action action) {
        undo.push(action);
        redo.clear();
    }

    public Action undoLastAction() {
        return undo.isEmpty() ? null : undo.pop();
    }
    public Action redoLastAction() {
        return redo.isEmpty() ? null : redo.pop();
    }
}