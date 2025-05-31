import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class javataskmanager extends JFrame {

    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInputField;
    private JButton addButton, deleteButton, editButton;

    private int editingIndex = -1; // To track if we are editing a task

    public javataskmanager() {
        setTitle("Java Swing Task Manager");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskInputField = new JTextField();
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        editButton = new JButton("Edit Task");

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        // Top part for input and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(5, 5));
        inputPanel.add(taskInputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        inputPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Task list in center with scroll
        panel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        add(panel);

        // Button Actions
        addButton.addActionListener(e -> addOrUpdateTask());
        deleteButton.addActionListener(e -> deleteTask());
        editButton.addActionListener(e -> editTask());

        // Optional: Double click on list item to edit
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editTask();
                }
            }
        });
    }

    private void addOrUpdateTask() {
        String taskText = taskInputField.getText().trim();
        if (taskText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a task.");
            return;
        }
        if (editingIndex == -1) {
            // Add new task
            taskListModel.addElement(taskText);
        } else {
            // Update existing task
            taskListModel.set(editingIndex, taskText);
            editingIndex = -1;
            addButton.setText("Add Task");
        }
        taskInputField.setText("");
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            taskListModel.remove(selectedIndex);
        }
    }

    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.");
            return;
        }
        String selectedTask = taskListModel.get(selectedIndex);
        taskInputField.setText(selectedTask);
        editingIndex = selectedIndex;
        addButton.setText("Update Task");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new javataskmanager().setVisible(true);
        });
    }
}
