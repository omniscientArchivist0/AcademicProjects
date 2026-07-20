package ui;

import object.BaseTask;
import object.Filter;

import java.util.List;
import java.util.Scanner;

public class TerminalUI implements BaseUI {
    public void createTask() {
        System.out.println("// Crie uma tarefa informando o seu texto e se ela esta completa ou não. //");
    }

    public void deleteTask() {
        System.out.println("// Escolha uma tarefa para ser deletada. //");
    }
    public BaseTask selectTask(List<BaseTask> baseTaskList) {
        Scanner sc = new Scanner(System.in);
        showList(baseTaskList);
        System.out.println("// Selecione a tarefa que deseja alterar. O padrão é a primeira (0). [0 - " + (baseTaskList.size() - 1) + "] //");
        int selectedTask = sc.nextInt();
        if (selectedTask > baseTaskList.size() - 1) {
            selectedTask = 0;
        }
        return baseTaskList.get(selectedTask);
    }

    public void updateTask() {
        System.out.println("// Altere o estado de uma tarefa a sua escolha. //");
    }

    public void showList(List<BaseTask> baseTaskList) {
        System.out.println("// [Lista de Tarefas] //");
        System.out.println("// ------------------ //");
        if (baseTaskList.isEmpty()) {
            System.out.println("// Nenhuma tarefa foi criada. //");
        }
        for (BaseTask baseTask : baseTaskList) {
            System.out.println(
                    "| " + "Task [" + (baseTask.getInternalID() - 1) + "]"
                            + "\n| " + baseTask.getText()
                            + "\n| " + (baseTask.isCompleted() ? "<Finalizado>" : "<Não-finalizado>")
                            + "\n| " + baseTask.getDate().toLocalDate().toString()
                            + "\n"
            );
        }
    }

    public Filter setFilter() {
        Scanner sc = new Scanner(System.in);
        System.out.println("// Altere o filtro da lista de tarefas em função de seu status. O padrão é All. (All/True/False)//");
        String filter = sc.nextLine();
        filter = filter.toUpperCase();
        return switch (filter) {
            case "TRUE" -> Filter.TRUE;
            case "FALSE" -> Filter.FALSE;
            default -> Filter.ALL;
        };
    }

    public String taskTextInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o conteudo da tarefa:");
        return sc.nextLine();
    }
    public boolean taskBooleanInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("A tarefa está concluida ou não? (True/False)");
        return sc.nextBoolean();
    }

    public int boot() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n// Lista de Tarefas - Aplicação //");
        System.out.println("As suas opções de ação são: ");
        System.out.println("1 - Criar uma tarefa.");
        System.out.println("2 - Exibir todas as tarefas.");
        System.out.println("3 - Alterar o filtro de exibição das tarefas.");
        System.out.println("4 - Remover uma tarefa.");
        System.out.println("5 - Alterar o estado de uma tarefa.");
        System.out.println("6 - Encerrar o programa.");
        return sc.nextInt();
    }
}
