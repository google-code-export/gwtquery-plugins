/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtquery.plugins.droppable.client.gfindersample;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The data source for contact information used in the sample. This majority of
 * the code below comes from the GWT showcase :
 * http://gwt.google.com/samples/Showcase/Showcase.html#!CwCellList
 * 
 */
public class FileSystem {

  /**
   * File type
   */
  public static enum FileType {
    DIRECTORY, FILE;
  }

  /**
   * File object
   */
  public static class File implements Comparable<File> {

    /**
     * The key provider that provides the unique ID of a contact.
     */
    public static final ProvidesKey<File> KEY_PROVIDER = new ProvidesKey<File>() {
      public Object getKey(File item) {
        return item == null ? null : item.getId();
      }
    };

    private static int nextId = 0;

    private String name;
    private FileType type;
    private final int id;
    private File parent;

    public File(FileType type) {
      this.type = type;
      this.id = nextId;
      nextId++;
    }

    public int compareTo(File o) {
      if (o == null) {
        return -1;
      }
      if (o.getType() != type) {
        return (type == FileType.DIRECTORY) ? 1 : -1;
      }
      return (o.name == null) ? -1 : -o.name.compareTo(name);
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof File) {
        return id == ((File) o).id;
      }
      return false;
    }

    @Override
    public int hashCode() {
      return id;
    }

    /**
     * @return the contact's address
     */
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    /**
     * @return the type of the file
     */
    public FileType getType() {
      return type;
    }

    /**
     * @return the unique ID of the contact
     */
    public int getId() {
      return this.id;
    }

    public File getParent() {
      return parent;
    }

    public void setParent(File parent) {
      this.parent = parent;
    }

  }

  private static final String[] LETTERS = { "a", "b", "c", "d", "e", "f", "g",
      "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
      "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
      "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
      "X", "Y", "Z" };

  /*
   * private static final String[] FILE_NAMES = { "chmod", "date",
   * "domainname  expr", "ksh", "ln", "mv", "pwd", "rmdir", "stty", "test",
   * "zsh", "bash", "cp", "dd", "echo", "hostname", "launchctl", "ls", "pax",
   * "rcp", "sh", "sync", "unlink", "cat", "csh", "df", "ed", "kill", "link",
   * "mkdir", "ps", "rm", "sleep", "tcsh", "wait4path", "Address", "Book",
   * "Adobe", "Reader", "9", "AppleScript", "Automator", "Belgium", "Identity",
   * "Card", "Calculator", "Chess", "ChromeWithSpeedTracer", "DVD", "Player",
   * "Dashboard", "Dictionary", "Firefox", "Font", "Book", "Front", "Row",
   * "GarageBand", "Gimp", "Google", "Chrome", "Home'Bank", "Image", "Capture",
   * "Mail", "Microsoft", "Internet", "Explorer", "6", "Microsoft", "Internet",
   * "Explorer", "7", "Microsoft", "Office", "2008", "OpenOffice.org", "Opera",
   * "Photo", "Booth", "Preview", "QuickTime", "Player", "Safari", "SketchUp",
   * "Skype", "Smultron", "Stickies", "System", "Preferences", "TextEdit",
   * "Time", "Machine", "Utilities", "VLC", "Wine", "WineBottler", "XAMPP",
   * "eclipse", "iCal", "iChat", "iDVD", "iMovie", "iPhoto", "iSync", "iTunes",
   * "iWeb", "PICT0002", "PICT0004", "PICT0005", "PICT0006", "PICT0007",
   * "PICT0010", "PICT0011", "PICT0012", "PICT0013", "PICT0014", "PICT0015",
   * "PICT0016", "PICT0017", "PICT0018", "PICT0019", "PICT0020", "PICT0021",
   * "PICT0022", "PICT0023", "PICT0024", "PICT0025", "PICT0026", "PICT0027",
   * "PICT0028", "PICT0029", "PICT0030", "PICT0031", "PICT0032", "PICT0033",
   * "PICT0034", "PICT0035", "PICT0036", "PICT0037", "PICT0038", "PICT0039",
   * "PICT0040", "PICT0041", "PICT0042", "PICT0043", "PICT0044", "PICT0045",
   * "PICT0046", "PICT0047", "PICT0048", "PICT0049", "PICT0050", "PICT0051",
   * "PICT0052", "PICT0053", "PICT0054", "PICT0055", "PICT0056", "PICT0057",
   * "PICT0058", "PICT0059", "PICT0060", "PICT0061", "PICT0062", "PICT0063",
   * "PICT0064", "PICT0068", "PICT0069", "PICT0070", "PICT0071", "PICT0072",
   * "PICT0073", "PICT0074", "PICT0075", "PICT0076", "PICT0077", "PICT0078",
   * "PICT0080", "PICT0081", "Thumbs" };
   */
  private static final String[] ROOT_DIRECTORY_NAMES = { "Applications",
      "Library", "Network", "System", "Users", "Volumes", "bin", "cores",
      "dev", "etc", "home", "net", "private", "sbin", "tmp", "usr", "var" };

  private static final String[] OTHER_DIRECTORY_NAME = { "Application",
      "Support", "Audio", "Automator", "Caches", "ColorPickers", "ColorSync",
      "Components", "Compositions", "Contextual", "Menu", "Items", "Desktop",
      "Pictures", "Developer", "Dictionaries", "Documentation", "Extensions",
      "Filesystems", "Fonts", "Fonts", "Disabled", "Frameworks", "Google",
      "Graphics", "Image", "Capture", "Input", "Methods", "Internet",
      "Plug-Ins", "Java", "Keyboard", "Layouts", "Keychains", "LaunchAgents",
      "LaunchDaemons", "Logs", "Modem", "Scripts", "Mozilla", "OpenSC", "PDF",
      "Services", "Perl", "PreferencePanes", "Preferences", "Printers",
      "PrivilegedHelperTools", "Python", "QuickLook", "QuickTime", "Receipts",
      "Ruby", "Sandbox", "Screen", "Savers", "Scripts", "Security", "Speech",
      "Spelling", "Spotlight", "StartupItems", "Updates", "User", "Pictures",
      "WebServer", "Widgets", "iTunes", "James", "John", "Robert", "Michael",
      "William", "David", "Richard", "Charles", "Joseph", "Thomas",
      "Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth",
      "Steven", "Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason",
      "Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey", "Frank",
      "Scott", "Eric", "Stephen", "Andrew", "Raymond", "Gregory", "Joshua",
      "Jerry", "Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas",
      "Henry", "Carl", "Arthur", "Ryan", "Roger", "Joe", "Juan", "Jack",
      "Albert", "Jonathan", "Justin", "Terry", "Gerald", "Keith", "Samuel",
      "Willie", "Ralph", "Lawrence", "Nicholas", "Roy", "Benjamin", "Bruce",
      "Brandon", "Adam", "Harry", "Fred", "Wayne", "Billy", "Steve", "Louis",
      "Jeremy", "Aaron", "Randy", "Howard", "Eugene", "Carlos", "Russell",
      "Bobby", "Victor", "Martin", "Ernest", "Phillip" };

  private static final String[] FILE_EXTENSIONS = { "blg", "csv", "dat", "efx",
      "key", "pps", "ppt", "pptx", "sdf", "vcf", "xml", "aac", "aif", "iff",
      "m3u", "mid", "mp3", "mpa", "ra", "wav", "wma", "3g2", "3gp", "asf",
      "asx", "avi", "flv", "mov", "mp4", "mpg", "rm", "swf", "vob", "wmv",
      "3dm", "max", "bmp", "gif", "jpg", "png", "psd", "thm", "tif", "yuv",
      "ai", "drw", "eps", "ps", "svg", "indd", "pct", "pdf", "qxd", "qxp",
      "wks", "xls", "xlsx", "accdb", "db", "mdb", "pdb", "sql", "app", "bat",
      "cgi", "com", "exe", "jar", "pif", "vb", "wsf", "asp", "cer", "csr",
      "css", "hª", "hªl", "js", "jsp", "php", "rss", "xhtml", "fnt", "fon",
      "otf", "ttf", "cab", "cpl", "cur", "dll", "dmp", "drv", "lnk", "sys",
      "cfg", "ini", "prf", "bin", "hqx", "mim", "uue", "deb", "gz", "pkg",
      "rar", "sit", "sitx", "tar.gz", "zip", "zipx", "dmg", "iso", "c",
      "class", "cpp", "cs", "dtd", "java", "m", "pl" };

  private static int MAX_DEPTH = 20;
  private static int MAX_FILE_NBR = 10;
  private static int MAX_DIR_NBR = 5;
  private static int MAX_LETTER_FILE_NAME = 30;

  /**
   * The singleton instance of the database.
   */
  private static FileSystem instance;

  /**
   * Get the singleton instance of the contact database.
   * 
   * @return the singleton instance
   */
  public static FileSystem get() {
    if (instance == null) {
      instance = new FileSystem();
    }
    return instance;
  }

  /**
   * The provider that holds the list of file in a directory.
   */
  private Map<File, ListDataProvider<File>> filesByDirectory = new HashMap<File, ListDataProvider<File>>();
  private List<File> rootDirectories = new ArrayList<File>();

  /**
   * Construct the file system.
   */
  private FileSystem() {
    for (String name : ROOT_DIRECTORY_NAMES) {
      File root = new File(FileType.DIRECTORY);
      root.setName(name);
      rootDirectories.add(root);
      generateDirTree(root, Random.nextInt(MAX_FILE_NBR), Random
          .nextInt(MAX_DIR_NBR), 0);
    }
  }

  private void generateDirTree(File root, int fileNbr, int dirNbr, int depth) {
    assert root.getType() == FileType.DIRECTORY;
    ListDataProvider<File> files = new ListDataProvider<File>();

    if (depth < Random.nextInt(MAX_DEPTH)) {
      // generate directories
      File dir = generateDirectory(root);
      // files.getList().remove(dir);
      // files.getList().remove(dir);
      files.getList().add(dir);
      generateDirTree(dir, Random.nextInt(MAX_FILE_NBR), Random
          .nextInt(MAX_DIR_NBR), depth + 1);
    }
    
    // ensure at least one file by directory
    fileNbr = Math.max(fileNbr, 1);

    for (int i = 0; i < fileNbr; i++) {
      File f = generateFile(root);
      // avoid duplicate
      // files.getList().remove(f);
      files.getList().add(f);

    }
    
    filesByDirectory.put(root, files);

  }

  private File generateFile(File parent) {
    File f = new File(FileType.FILE);
    int letterNbr = Random.nextInt(MAX_LETTER_FILE_NAME);
    StringBuilder nameBuilder = new StringBuilder();
    for (int i = 0; i < letterNbr; i++) {
      nameBuilder.append(nextValue(LETTERS));
    }

    String name = nameBuilder.append(".").append(nextValue(FILE_EXTENSIONS))
        .toString();
    f.setName(name);
    f.setParent(parent);
    return f;
  }

  private File generateDirectory(File parent) {
    File f = new File(FileType.DIRECTORY);
    String name = nextValue(OTHER_DIRECTORY_NAME);
    f.setName(name);
    f.setParent(parent);
    return f;
  }

  /**
   * move file
   * 
   * @param contact
   *          the contact to add.
   */
  public void moveFile(File file, File destination) {
    File oldDestination = file.getParent();
    List<File> previousList = getFilesDataProvider(oldDestination).getList();
    List<File> newList = getFilesDataProvider(destination).getList();
    previousList.remove(file);
    // newList.remove(contact);
    newList.add(file);

    refreshDisplays(oldDestination);
    refreshDisplays(destination);
  }

  /**
   * Add a display to the database. The current range of interest of the display
   * will be populated with data.
   * 
   * @param display
   *          a {@Link HasData}.
   */
  /*
   * public void addDataDisplay(HasData<File> display, File dir) { assert
   * dir.getType() == FileType.DIRECTORY;
   * getFilesDataProvider(dir).addDataDisplay(display); }
   */

  /**
   * Get the root directories.
   * 
   * @return the categories in the database
   */
  public List<File> getRootDirectories() {
    return rootDirectories;
  }

  /**
   * Query all files for a directory.
   * 
   * @param category
   *          the category
   * @return the list of contacts in the category
   */
  public List<File> getFilesByDirectory(File directory) {
    return new ArrayList<File>(getFilesDataProvider(directory).getList());
  }

  /**
   * Refresh all displays.
   */
  public void refreshDisplays() {
    for (ListDataProvider<File> list : filesByDirectory.values()) {
      list.refresh();
    }
  }

  /**
   * Refresh displays by category.
   */
  public void refreshDisplays(File d) {
    assert d.getType() == FileType.DIRECTORY;
    filesByDirectory.get(d).refresh();
  }

  /**
   * Get the next random value from an array.
   * 
   * @param array
   *          the array
   * @return a random value in the array
   */
  private <T> T nextValue(T[] array) {
    return array[Random.nextInt(array.length)];
  }

  public ListDataProvider<File> getFilesDataProvider(File dir) {
    return filesByDirectory.get(dir);
  }

}
