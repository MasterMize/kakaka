import java.util.*;
import java.io.*;
class Word{
    public String word_target;
    public String word_explain;
    public Word(){

    }
    public Word(String word_target, String word_explain){
        this.word_target = word_target;
        this.word_explain = word_explain;
    }
    public String getWT(){
        return this.word_target;
    }
    public String getWE(){
        return this.word_explain;
    }
    public void setWT(String word_target){
        this.word_target = word_target;
    }
    public void setWE(String word_explain){
        this.word_explain = word_explain;
    }
    public void setWord(String word_target, String word_explain){
        this.word_target = word_target;
        this.word_explain = word_explain;
    }
}
class Dictionary {
    
    public ArrayList<Word> listWord = new ArrayList<>();
    public Dictionary(){
        Word word = new Word("Hello", "Xin chao");
        listWord.add(word);
    }
}

class DictionaryManagement extends Dictionary{
    
    public void insertFromCommanline(){
        System.out.print("Từ mới: "); 
        Scanner input1 = new Scanner(System.in);  
        String word_target_new = input1.nextLine();
        System.out.print("Nghĩa: ");
        Scanner input2 = new Scanner(System.in);
        String word_explain_new = input2.nextLine();
        Word word_new = new Word(word_target_new, word_explain_new);
        listWord.add(word_new);
    }

    public void insertFromFile() {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập tên file thêm vào : ");
        String file_name = input.nextLine();
        File file = new File(file_name);
        
        try (FileReader fr = new FileReader(file)) {
            char [] a = new char[250];
            
            fr.read(a);   // reads the content to the array
            StringBuilder word_target = new StringBuilder();
            StringBuilder word_explain = new StringBuilder();
            String word_target_new;
            String word_explain_new;
            int change = 0;
            for(char c : a)
            {
                if(c == '\t'){
                    change = 1;
                    continue;
                }
                if(c == '\n'){
                    word_target_new = word_target.substring(0);
                    word_explain_new = word_explain.substring(0);
                    Word word_new = new Word(word_target_new, word_explain_new);
                    listWord.add(word_new);
                    word_target.delete(0,word_target.length() +  1);
                    word_explain.delete(0, word_explain.length() + 1);
                    change = 0;
                    continue;
                }
                if(change == 0){
                    word_target.append(c);
                }
                else{
                    word_explain.append(c);
                }
            }
            System.out.println("Đã nhập vào từ file " + file_name);            
        }
        catch(IOException e) {
            System.out.println("Lỗi nhập vào từ file");    
        }       
    }

    public void dictionaryFix(){
        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        System.out.print("Nhập từ cần sửa : ");
        String word_fix = input1.nextLine();
        System.out.print("Sửa thành : ");
        String word_fixed = input2.nextLine();
        Word word_new = new Word(word_fix,word_fixed);
        listWord.forEach((Word word_find) -> {
            if(word_fix.equals(word_find.word_target) ){
                listWord.set(listWord.indexOf(word_find), word_new);
                
            }

        });
        
    }

    public void dictionaryDelete(){
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập từ cần xóa : ");
        String word_delete = input.nextLine();
        for(int i = 0 ; i < listWord.size(); ++i){
            if(word_delete.equals(listWord.get(i).word_target)){
                listWord.remove(i);
                break;
            }      
        }
    }

    public void dictionaryLookup(){
        System.out.print("Nhập từ cần tra cứu : ");
        Scanner input = new Scanner(System.in);
        String word_lookup = input.nextLine();
        listWord.forEach((Word word_find) -> {
            if(word_lookup.equals(word_find.word_target)){
                System.out.print("Nghĩa : ");
                System.out.println(word_find.word_explain);
            }
        });

    }

    public void dictionaryExportToFile(){
        Scanner input = new Scanner(System.in);
        System.out.print("Tên file xuất ra : ");
        String file_name = input.nextLine();
        File file = new File(file_name);
        
        try(FileWriter writer = new FileWriter(file)){
            file.createNewFile();
      
            for(int i = 0; i < listWord.size(); ++i){
                writer.write(listWord.get(i).word_target);
                writer.write("\t");
                writer.write(listWord.get(i).word_explain);
                writer.write("\n");
            }
            writer.flush();
            writer.close();
            System.out.println("Đã in ra file " + file_name);
        } catch(IOException e) {
            System.out.println("Lỗi in ra file" + file_name);
        }
    }
}

public class DictionaryCommanline extends DictionaryManagement{
    public void dictionaryBasic(){
        int numberWord;
        System.out.print("So luong tu muon nhap : ");
        Scanner input = new Scanner(System.in);
        numberWord = input.nextInt();
        
        for(int i = 0; i < numberWord; ++i){
            this.insertFromCommanline();
        }
        this.showAllWords();        
    }
    public void showAllWords(){
        System.out.println("No  |English      |Vietnamese");
        listWord.forEach((word_print) -> {           
            System.out.println(listWord.indexOf(word_print)+1 + "    " + word_print.word_target + "    " + word_print.word_explain);
        });        
    }

    public void dictionarySearcher(){
        Scanner input = new Scanner(System.in);
        System.out.print("Search: ");
        String word_search = input.nextLine();
        ArrayList<String> listWordSearch = new ArrayList<>();
        for(int i = 0; i < listWord.size(); ++i){
            String temp = listWord.get(i).word_target;
            temp = temp.substring(0, word_search.length());
            if(word_search.equals(temp)){
                listWordSearch.add(listWord.get(i).word_target);
            }
        }
        listWordSearch.forEach((String word_print)->{
            System.out.println("Kết quả tìm kiếm : ");
            System.out.println(word_print);
        });

    }

    public void dictionaryAdvanced(){
        
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        do
        {
            System.out.println("------------------------------------------");
            System.out.println("|        TU DIEN                          |");
            System.out.println("|1.Insert                                 |");
            System.out.println("|2.Insert from file                       |");
            System.out.println("|3.Show all words                         |");
            System.out.println("|4.Lookup                                 |");
            System.out.println("|5.Fix                                    |");
            System.out.println("|6.Delete                                 |");
            System.out.println("|7.Export to file                         |");
            System.out.println("|8.Search                                 |");
            System.out.println("|9.Exit                                   |");
            System.out.println("------------------------------------------");
        
            int select = input.nextInt();
            switch (select){
                case 1 : 
                    this.dictionaryBasic();
                    break;
                case 2 :
                    this.insertFromFile();
                    break;
                case 3 :
                    this.showAllWords();
                    break;
                case 4 : 
                    this.dictionaryLookup();
                    break;
                case 5 :
                    this.dictionaryFix();
                    break;
                case 6 :
                    this.dictionaryDelete();
                    break;
                case 7 : 
                    this.dictionaryExportToFile();
                    break;
                case 8 :
                    this.dictionarySearcher();
                default:
                    exit = true;
                    break;
            }
        }
        while (!exit);    
    }

    public static void main(String[] args) {
        DictionaryCommanline test = new DictionaryCommanline();
         test.dictionaryAdvanced();  
        
    }
}









