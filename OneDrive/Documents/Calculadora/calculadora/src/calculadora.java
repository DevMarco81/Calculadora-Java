import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Classe Calculadora: Implementa uma calculadora com interface gráfica usando Swing
 * Funcionalidades: Adição, Subtração, Multiplicação, Divisão, Porcentagem, Inversão de sinal
 */
public class calculadora {
    
    // ========== DIMENSÕES DA JANELA ==========
    /** Largura da janela da calculadora em pixels */
    int boardWidth = 360;
    /** Altura da janela da calculadora em pixels */
    int boardHeight = 540;

    // ========== CORES PERSONALIZADAS ==========
    /** Cor cinza claro para botões superiores */
    Color customLightGray = new Color(212, 212, 212);
    /** Cor cinza escuro para botões numéricos */
    Color customDarkGray = new Color(80, 80, 80);
    /** Cor preta para fundo */
    Color customBlack = new Color(28, 28, 28);
    /** Cor laranja para operadores */
    Color customOrange = new Color(255, 149, 0);

    // ========== VALORES DOS BOTÕES ==========
    /** Array contendo todos os símbolos e números que aparecem nos botões (4x5 = 20 botões) */
    String[] buttonsValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "=" 
    };
    /** Array dos símbolos que ficam na coluna direita (operadores e igual) */
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    /** Array dos símbolos que ficam na linha superior (funções especiais) */
    String[] topSymbols = {"AC", "+/-", "%"};
    
    // ========== COMPONENTES DA INTERFACE GRÁFICA ==========
    /** Janela principal da aplicação */
    JFrame frame = new JFrame("Calculadora");
    /** Rótulo que exibe o número/resultado na tela */
    JLabel displaylabel = new JLabel();
    /** Painel que contém o display */
    JPanel displayPanel = new JPanel();
    /** Painel que contém todos os botões */
    JPanel buttonsPanel = new JPanel();

    // ========== VARIÁVEIS DA LÓGICA DE CÁLCULO ==========
    /** Primeiro operando da operação matemática */
    String A = "0";
    /** Operador selecionado (+, -, ×, ÷) */
    String operator = null;
    /** Segundo operando da operação matemática */
    String B = null;
    /** Flag para indicar que um novo número deve ser digitado */
    boolean isNewNumber = false;

    /**
     * Construtor da Calculadora
     * Responsável por:
     * - Configurar a janela principal
     * - Criar o display (tela)
     * - Criar todos os botões (20 no total)
     * - Adicionar listeners para ações dos botões
     */
    calculadora(){

            // frame.setVisible(true);
            frame.setSize(boardWidth, boardHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // ========== CONFIGURAÇÃO DO DISPLAY ==========
            displaylabel.setBackground(customBlack);
            displaylabel.setForeground(Color.white);
            displaylabel.setFont(new Font("Arial", Font.PLAIN, 80));
            displaylabel.setHorizontalAlignment(JLabel.RIGHT);
            displaylabel.setText("0");
            displaylabel.setOpaque(true);

            displayPanel.setLayout(new BorderLayout());
            displayPanel.add(displaylabel);
            frame.add(displayPanel, BorderLayout.NORTH);

            // ========== CONFIGURAÇÃO DO PAINEL DE BOTÕES ==========
            buttonsPanel.setLayout(new GridLayout(5, 4));
            buttonsPanel.setBackground(customBlack);
            frame.add(buttonsPanel);

            // ========== CRIAÇÃO E CONFIGURAÇÃO DOS BOTÕES ==========
            for (int i = 0; i < buttonsValues.length; i++) {
                JButton button = new JButton();
                String buttonsValue = buttonsValues[i];
                button.setFont(new Font("Arial", Font.PLAIN, 30));
                button.setText(buttonsValue);
                button.setFocusable(false);
                button.setBorder(new LineBorder(customBlack));
                if (Arrays.asList(topSymbols).contains(buttonsValue)) {
                    button.setBackground(customLightGray);
                    button.setForeground(customBlack);
                }
                else if (Arrays.asList(rightSymbols).contains(buttonsValue)){
                    button.setBackground(customOrange);
                    button.setForeground(Color.white);
                }
                else {
                    button.setBackground(customDarkGray);
                    button.setForeground(Color.white);
                }

                buttonsPanel.add(button);

                button.addActionListener(new ActionListener() {
                    /**
                     * Método que trata os cliques nos botões
                     * Define o comportamento de cada tipo de botão (operadores, números, funções)
                     */
                    public void actionPerformed(ActionEvent e){
                        JButton button = (JButton) e.getSource();
                        String buttonValue = button.getText();
                        if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                            // OPERADOR IGUAL (=)
                            if (buttonValue.equals("=")) {
                                // Verifica se operator não é null antes de calcular
                                if (operator != null) {
                                    B = displaylabel.getText();
                                    double numA = Double.parseDouble(A);
                                    double numB = Double.parseDouble(B);

                                    // Executa a operação baseada no operador selecionado
                                    if (operator.equals("+")) {
                                        displaylabel.setText(removeZeroDecimal(numA+numB));
                                    }
                                else if (operator.equals("-")) {
                                        displaylabel.setText(removeZeroDecimal(numA-numB));
                                    }
                                    else if (operator.equals("×")) {
                                        displaylabel.setText(removeZeroDecimal(numA*numB));
                                    }
                                    else if (operator.equals("÷")) {
                                        displaylabel.setText(removeZeroDecimal(numA/numB));
                                    }
                                    clearAll();
                                    isNewNumber = true;
                                }
                            }
                            // OPERADORES BÁSICOS (+, -, ×, ÷)
                            else if ("+-×÷".contains(buttonValue)) {
                                // Se já existe um operador e não é um novo número, calcula antes
                                if (operator != null && !isNewNumber) {
                                    B = displaylabel.getText();
                                    double numA = Double.parseDouble(A);
                                    double numB = Double.parseDouble(B);
                                    
                                    if (operator.equals("+")) {
                                        displaylabel.setText(removeZeroDecimal(numA+numB));
                                    }
                                    else if (operator.equals("-")) {
                                        displaylabel.setText(removeZeroDecimal(numA-numB));
                                    }
                                    else if (operator.equals("×")) {
                                        displaylabel.setText(removeZeroDecimal(numA*numB));
                                    }
                                    else if (operator.equals("÷")) {
                                        displaylabel.setText(removeZeroDecimal(numA/numB));
                                    }
                                }
                                A = displaylabel.getText();
                                displaylabel.setText("0");
                                B = "0";
                                operator = buttonValue;
                                isNewNumber = true;
                            }

                        }
                        // BOTÕES SUPERIORES (AC, +/-, %) E RAIZ QUADRADA
                        else if (Arrays.asList(topSymbols).contains(buttonValue) || buttonValue.equals("√")) {
                            // Limpar tudo (All Clear)
                            if (buttonValue.equals("AC")) {
                                clearAll();
                                displaylabel.setText("0");
                                isNewNumber = false;

                            }
                            // Trocar sinal (+/-)
                            else if (buttonValue.equals("+/-")) {
                                double numDisplay = Double.parseDouble(displaylabel.getText());
                                numDisplay *= -1;
                                displaylabel.setText(removeZeroDecimal(numDisplay));
                                
                            }
                            // Porcentagem (%)
                            else if (buttonValue.equals("%")) {
                                double numDisplay = Double.parseDouble(displaylabel.getText());
                                numDisplay /= 100;
                                displaylabel.setText(removeZeroDecimal(numDisplay));

                            }
                            // Raiz quadrada (√)
                            else if (buttonValue.equals("√")) {
                                double numDisplay = Double.parseDouble(displaylabel.getText());
                                numDisplay = Math.sqrt(numDisplay);
                                displaylabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                        // NÚMEROS E PONTO DECIMAL
                        else { //digits or .
                            // Ponto decimal (.)
                            if (buttonValue.equals(".")) {
                                // Adiciona ponto apenas se não houver um já no display
                                if (!displaylabel.getText().contains(buttonValue)) {
                                    displaylabel.setText(displaylabel.getText() + buttonValue);
                                }

                            }
                            // Dígitos (0-9)
                            else if ("0123456789".contains(buttonValue)) {
                                // Se deve começar um novo número, inicia com este dígito
                                if (isNewNumber) {
                                    displaylabel.setText(buttonValue);
                                    isNewNumber = false;
                                }
                                // Se o display está em "0", substitui pelo novo dígito
                                else if (displaylabel.getText().equals("0")) {
                                    displaylabel.setText(buttonValue);
                                }
                                // Caso contrário, concatena o dígito
                                else {
                                    displaylabel.setText(displaylabel.getText() + buttonValue);
                                }

                            }

                        } 
                    }
                    
                });
            }
            frame.setVisible(true);
    
    }

    /**
     * Método clearAll(): Limpa todos os valores da calculadora
     * Reseta A (primeiro operando), operator e B (segundo operando) aos seus valores iniciais
     * Usado após calcular um resultado
     */
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
        isNewNumber = false;
    }

    /**
     * Método removeZeroDecimal(double numDisplay): Remove zeros decimais desnecessários
     * Se o número é um inteiro (ex: 5.0), exibe apenas "5"
     * Se o número tem decimais (ex: 5.5), mantém o formato completo
     * @param numDisplay O número a ser formatado
     * @return String com o número formatado
     */
    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
    
    /**
     * Método main(String[] args): Ponto de entrada da aplicação
     * Cria uma nova instância da calculadora, iniciando a interface gráfica
     */
    public static void main(String[]args) {
        new calculadora();
    }
}
