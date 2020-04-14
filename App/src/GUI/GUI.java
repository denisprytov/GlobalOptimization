package GUI;

import Algorithms.PiyavskiMethod;
import Algorithms.SerialScanAlgorithm;
import Algorithms.StronginMethod;
import General.Formula;
import General.Interval;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;


public class GUI {
    public static void main(String[] args) {


        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeriesCollection datasetX = new XYSeriesCollection();
        JFrame frame = new JFrame("Поиск глобального минимума функции F(x)");
        final JPanel mainPanel = new JPanel(new GridLayout(9,1,0,5));
        JPanel functionPanel = new JPanel(new GridLayout(1,2,0,5));
        JPanel rangePanel = new JPanel(new GridLayout(1,2,0,5));
        JPanel a = new JPanel(new GridLayout(1,2,0,5));
        a.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        JPanel b = new JPanel(new GridLayout(1,2,0,5));
        b.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        JPanel paramPanel = new JPanel(new GridLayout(1,2,0,5));
        JPanel m = new JPanel(new GridLayout(1,2,0,5));
        m.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        JPanel r = new JPanel(new GridLayout(1,2,0,5));
        r.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        //JPanel iterPanel = new JPanel(new GridLayout(1,2,0,5));
        //JPanel accurPanel = new JPanel(new GridLayout(1,2,0,5));


        JLabel func = new JLabel("Функция F(x): ");
        func.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        final JTextField function= new JTextField("2sin(3x) + 3cos(5x)");
        functionPanel.add(func);
        functionPanel.add(function);
        mainPanel.add(functionPanel);

        JLabel aLabel = new JLabel("a:");
        final JTextField aTextField = new JTextField("0");
        JLabel bLabel = new JLabel("b:");
        final JTextField bTextField = new JTextField("8");
        a.add(aLabel);
        a.add(aTextField);
        b.add(bLabel);
        b.add(bTextField);
        rangePanel.add(a);
        rangePanel.add(b);
        mainPanel.add(rangePanel);

        JLabel mLabel = new JLabel("m:");
        final JTextField mTextField = new JTextField("2");
        JLabel rLabel = new JLabel("r: ");
        final JTextField rTextField = new JTextField("2");
        m.add(mLabel);
        m.add(mTextField);
        r.add(rLabel);
        r.add(rTextField);
        paramPanel.add(m);
        paramPanel.add(r);
        mainPanel.add(paramPanel);

        final JPanel boxes =new JPanel(new GridLayout(1,2,0,5));
        final JComboBox comboBox1 = new JComboBox();
        comboBox1.addItem("Количесвто итераций");
        comboBox1.addItem("Точность");
        boxes.add(comboBox1);
        final JComboBox comboBox2 = new JComboBox();
        comboBox2.addItem("50");
        comboBox2.addItem("100");
        comboBox2.addItem("200");
        comboBox2.addItem("400");
        comboBox2.addItem("800");
        boxes.add(comboBox2);
        comboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(comboBox1.getSelectedIndex()==0){
                    comboBox2.removeAllItems();
                    comboBox2.addItem("50");
                    comboBox2.addItem("100");
                    comboBox2.addItem("200");
                    comboBox2.addItem("400");
                    comboBox2.addItem("800");
                }
                if(comboBox1.getSelectedIndex()==1){
                    comboBox2.removeAllItems();
                    comboBox2.addItem("0.1");
                    comboBox2.addItem("0.01");
                    comboBox2.addItem("0.001");
                }
            }
        });
        mainPanel.add(boxes);






        JButton draw = new JButton("Отрисовать функцию");
        JButton scanMethod = new JButton("Метод последовательного сканирования");
        JButton piyavskyMethod = new JButton("Метод Пиявского");
        JButton stronginMethod = new JButton("Метод Стронгина");
        JButton clear = new JButton("Очистить поле");
        mainPanel.add(draw);
        mainPanel.add(scanMethod);
        mainPanel.add(piyavskyMethod);
        mainPanel.add(stronginMethod);
        mainPanel.add(clear);

        draw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                Formula formula = new Formula(expression);
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                XYSeries base = new XYSeries("Функция");
                for(double i = left; i < right; i+=0.01){
                    base.add(i, formula.f(i));
                }
                dataset.addSeries(base);
                draw.setEnabled(false);
            }
        });

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                true);
        chart.setBackgroundPaint(Color.WHITE);

        final XYPlot plot = chart.getXYPlot();
        plot.setDataset(0,dataset);
        plot.setBackgroundPaint(new Color(232, 232, 232));

        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint  (0, Color.gray);
        renderer.setSeriesPaint(1,Color.DARK_GRAY);
        renderer.setSeriesLinesVisible (1, false);
        renderer.setSeriesPaint(2, Color.blue);
        renderer.setSeriesLinesVisible (2, false);
        renderer.setSeriesPaint(3, Color.red);
        renderer.setSeriesLinesVisible (3, false);
        renderer.setSeriesStroke(0, new BasicStroke(4));
        plot.setRenderer(0,renderer);

        frame.add(mainPanel, BorderLayout.WEST);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.9;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new ChartPanel(chart), gbc);

        JLabel scanMethodOutput1 = new JLabel("1. Метод последовательного сканирования:    Число сделанных итераций:");
        scanMethodOutput1.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        JLabel scanMethodOutput2 = new JLabel("Минимальное значение функции:");
        scanMethodOutput2.setBorder(BorderFactory.createEmptyBorder(0, 290,0,0));
        gbc.weighty = 0.01;
        gbc.gridy = 1;
        panel.add(scanMethodOutput1, gbc);
        gbc.weighty = 0.01;
        gbc.gridy = 2;
        panel.add(scanMethodOutput2, gbc);

        JLabel piyavskyMethodOutput1 = new JLabel("2. Метод Пиявского:    Число сделанных итераций:");
        piyavskyMethodOutput1.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        JLabel piyavskyMethodOutput2 = new JLabel("Минимальное значение функции:");
        piyavskyMethodOutput2.setBorder(BorderFactory.createEmptyBorder(0, 150,0,0));
        gbc.weighty = 0.01;
        gbc.gridy = 3;
        panel.add(piyavskyMethodOutput1, gbc);
        gbc.weighty = 0.01;
        gbc.gridy = 4;
        panel.add(piyavskyMethodOutput2, gbc);

        JLabel stronginMethodOutput1 = new JLabel("3. Метод Стронгина:    Число сделанных итераций:");
        stronginMethodOutput1.setBorder(BorderFactory.createEmptyBorder(0, 20,0,0));
        JLabel stronginMethodOutput2 = new JLabel("Минимальное значение функции:");
        stronginMethodOutput2.setBorder(BorderFactory.createEmptyBorder(0, 150,0,0));
        gbc.weighty = 0.01;
        gbc.gridy = 5;
        panel.add(stronginMethodOutput1, gbc);
        gbc.weighty = 0.01;
        gbc.gridy = 6;
        panel.add(stronginMethodOutput2, gbc);

        frame.add(panel, BorderLayout.CENTER);

        scanMethod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XYSeries serialPoints = new XYSeries("Точки последовательного сканирования");
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                Interval interval = new Interval(left,right);
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                SerialScanAlgorithm intervals = new SerialScanAlgorithm(interval, expression);
                int iters = 0;
                double accur = 0;
                int iterCount = 0;
                if(comboBox1.getSelectedIndex()==0){
                    iters = Integer.parseInt((String) comboBox2.getSelectedItem());
                    for(int i = 0; i<iters; i++){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                if(comboBox1.getSelectedIndex()==1){
                    accur = Double.parseDouble((String) comboBox2.getSelectedItem());
                    while(Collections.max(intervals.getLengths())>=accur){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                for(int i = 0; i<intervals.size();i++){
                    serialPoints.add((double)intervals.get(i).getLeft(),-5.0);
                }
                serialPoints.add((double)intervals.get(intervals.size()-1).getRight(), -5.0);
                dataset.addSeries(serialPoints);
                scanMethodOutput1.setText(scanMethodOutput1.getText() + " " + iterCount);
                scanMethodOutput2.setText(scanMethodOutput2.getText() + " " + intervals.getMinValue());
                scanMethod.setEnabled(false);
            }
        });

        piyavskyMethod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XYSeries piyavskiPoints = new XYSeries("Точки метода Пиявского");
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                Interval interval = new Interval(left,right);
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                double m = Double.parseDouble(mTextField.getText());
                PiyavskiMethod intervals = new PiyavskiMethod(m, interval, expression);
                int iters = 0;
                double accur = 0;
                int iterCount = 0;
                if(comboBox1.getSelectedIndex()==0){
                    iters = Integer.parseInt((String) comboBox2.getSelectedItem());
                    for(int i = 0; i<iters; i++){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                if(comboBox1.getSelectedIndex()==1){
                    accur = Double.parseDouble((String) comboBox2.getSelectedItem());
                    while(intervals.getLengths().get(intervals.getCharacteristics().indexOf(Collections.max(intervals.getCharacteristics())))>=accur){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                for(int i = 0; i<intervals.size();i++){
                    piyavskiPoints.add((double)intervals.get(i).getLeft(),-5.1);
                }
                piyavskiPoints.add((double)intervals.get(intervals.size()-1).getRight(), -5.1);
                dataset.addSeries(piyavskiPoints);
                piyavskyMethodOutput1.setText(piyavskyMethodOutput1.getText() + " " + iterCount);
                piyavskyMethodOutput2.setText(piyavskyMethodOutput2.getText() + " " + intervals.getMinValue());
                piyavskyMethod.setEnabled(false);
            }
        });

        stronginMethod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XYSeries stronginPoints = new XYSeries("Точки метода Стронгина");
                double left = Double.parseDouble(aTextField.getText());
                double right = Double.parseDouble(bTextField.getText());
                Interval interval = new Interval(left,right);
                Expression expression = new ExpressionBuilder(function.getText()).variables("x").build();
                double r = Double.parseDouble(rTextField.getText());
                StronginMethod intervals = new StronginMethod(r,interval,expression);

                int iters = 0;
                double accur = 0;
                int iterCount = 0;
                if(comboBox1.getSelectedIndex()==0){
                    iters = Integer.parseInt((String) comboBox2.getSelectedItem());
                    for(int i = 0; i<iters; i++){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                if(comboBox1.getSelectedIndex()==1){
                    accur = Double.parseDouble((String) comboBox2.getSelectedItem());
                    while(intervals.getLengths().get(intervals.getCharacteristics().indexOf(Collections.max(intervals.getCharacteristics())))>=accur){
                        intervals.makeNewIntervals();
                        iterCount++;
                    }
                }
                for(int i = 0; i<intervals.size();i++){
                    stronginPoints.add((double)intervals.get(i).getLeft(), -5.2);
                }
                stronginPoints.add((double)intervals.get(intervals.size()-1).getRight(), -5.2);
                dataset.addSeries(stronginPoints);
                stronginMethodOutput1.setText(stronginMethodOutput1.getText() + " " + iterCount);
                stronginMethodOutput2.setText(stronginMethodOutput2.getText() + " " + intervals.getMinValue());
                stronginMethod.setEnabled(false);
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataset.removeAllSeries();
                datasetX.removeAllSeries();
                scanMethodOutput1.setText("1. Метод последовательного сканирования:    Число сделанных итераций:");
                scanMethodOutput2.setText("Минимальное значение функции:");

                piyavskyMethodOutput1.setText("2. Метод Пиявского:    Число сделанных итераций:");
                piyavskyMethodOutput2.setText("Минимальное значение функции:");

                stronginMethodOutput1.setText("3. Метод Стронгина:    Число сделанных итераций:");
                stronginMethodOutput2.setText("Минимальное значение функции:");
                draw.setEnabled(true);
                scanMethod.setEnabled(true);
                piyavskyMethod.setEnabled(true);
                stronginMethod.setEnabled(true);
            }
        });

        //frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setSize(1200,900);
        frame.setVisible(true);
    }
}
