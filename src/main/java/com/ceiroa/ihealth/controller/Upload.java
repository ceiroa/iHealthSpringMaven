package com.ceiroa.ihealth.controller;

import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.daos.FileDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Upload {
	    
	private static String TMP_DIR_PATH ="";
    private File tmpDir;
    private static String DESTINATION_DIR_PATH="";
    private File destinationDir;
    
    private static FileDAO fileDAO;
    
	private String viewName;
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	 @Autowired
	public void setFileDAO(FileDAO fileDAO) {
		Upload.fileDAO = fileDAO;
	}


	public void init() {
            String os = System.getProperty("os.name");

            //Windows server
            if(os.startsWith("Win")) {
                DESTINATION_DIR_PATH = "C:\\Program Files\\Tomcat_7\\files";
                TMP_DIR_PATH = "C:\\Program Files\\Tomcat_7\\temp";
            } else {
            //Mac or server
                DESTINATION_DIR_PATH = "/Applications/Tomcat_7/files";
                TMP_DIR_PATH = "/Applications/Tomcat_7/tmp";
            }

            tmpDir = new File(TMP_DIR_PATH);

            if(!tmpDir.isDirectory()) {
                    throw new RuntimeException(TMP_DIR_PATH + " is not a directory");
            }
            //String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
            //destinationDir = new File(realPath);
            destinationDir = new File(DESTINATION_DIR_PATH);
            if(!destinationDir.isDirectory()) {
                    throw new RuntimeException(DESTINATION_DIR_PATH + " is not a directory");
            } 
    }

	@RequestMapping("/upload/view.htm")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("active", "manageUploads");
        return new ModelAndView(viewName, null);
    }
	
	@RequestMapping("/upload/send.htm")
    public ModelAndView send(HttpServletRequest request, HttpServletResponse response) {
        
    	init();
    	
    	request.setAttribute("active", "manageUploads");

        String clientId = request.getParameter("clientId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String postReqSource = request.getParameter("postReqSource");
        String numberOfFilesString = request.getParameter("numberOfFiles");

        int numberOfFiles = 0;
        if(numberOfFilesString!=null) {
            numberOfFiles = IntHelper.parseInt(numberOfFilesString);
            ArrayList<Integer> numberOfFilesArray = new ArrayList<Integer>();
            for(int i=1; i<=numberOfFiles; i++)
                    numberOfFilesArray.add(i);
            request.setAttribute("numberOfFilesArray", numberOfFilesArray);
        }
        request.setAttribute("clientId", clientId);
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("numberOfFiles", numberOfFilesString);
 
        StringBuilder message = new StringBuilder();

        //If the request is coming from the uploadFiles.jsp page
        if (postReqSource == null || !postReqSource.equals("manageUploads")) {

            //Save files
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

            //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(12 * 1024 * 1024); //12 MB

             //Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(tmpDir);

            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

            try {
                //Parse the request
                List<?> items = uploadHandler.parseRequest(request);
                Iterator<?> itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    //Handle Form Fields.
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        if(fieldName.equals("clientId")) {
                            clientId = item.getString();
                        } else if (fieldName.equals("firstName")) {
                            firstName = item.getString();
                        } else if (fieldName.equals("lastName")) {
                            lastName = item.getString();
                        }
                    //Handle Uploaded files.
                    } else {
                        //Create Directory with Client Name
                        String destinationDirString = destinationDir.toString() 
                                + File.separator + firstName + " " + lastName;
                        File destinationDir2 = new File(destinationDirString);
                        if(!destinationDir2.exists()) {
                            destinationDir2.mkdir();
                        }
                        String fileName = item.getName();
                        if(fileName.length() > 40) {
                            String fileType = fileName.substring((fileName.length()-4),
                                    (fileName.length()));
                            String newFileName = fileName.substring(0, 36);
                            fileName = newFileName + fileType;
                        }
                        File file = new File(destinationDir2, fileName);

                        //Write file to the ultimate location.
                        item.write(file);
                        //If the item has been successfully uploaded, save information to the database
                        if(file.exists()) {
                            int result = fileDAO.insert(clientId, file);
                            if(result==1) {
                                message.append("File ").append(file.getName()).append(" successfully uploaded.<br/>");
                            } else {
                                //Information was not inserted in the DB.
                                file.delete();
                                message.append("Problem inserting info for file ").append(file.getName()).append(" in database. Please try again or contact support.<br/>");
                            }
                        } else {
                            message.append("File could not be uploaded. Please try again or "
                                    + "contact support.<br/>");
                        }
                    }
                }
            } catch (FileUploadException ex) {
                message.append("Error encountered while parsing the request.");
            } catch (Exception ex) {
                message.append("Error encountered while uploading file.");
            }
        }

        request.setAttribute("infoMessage", message);

        return new ModelAndView(viewName, null);
    }
}
