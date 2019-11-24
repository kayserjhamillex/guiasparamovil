//
//  ImageUploadController.swift
//  Appointments
//
//  Created by Aalim Mulji on 5/3/19.
//  Copyright © 2019 Aalim Mulji. All rights reserved.
//

import UIKit
import Firebase
import SVProgressHUD

class ImageUploadController: UIViewController, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    //MARK:- IBOutlets
    @IBOutlet weak var profilePictureImageView: UIImageView!
    @IBOutlet weak var saveAndProceedButton: UIButton!
    
    //MARK:- Global Variables
    let storage = Storage.storage()
    var userProfessor = Professor()
    var student = Student()
    var userType = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    @IBAction func uploadButtonPressed(_ sender: Any) {
        
        let imagePickerController = UIImagePickerController()
        imagePickerController.delegate = self
        imagePickerController.allowsEditing = true
        
        let actionSheet = UIAlertController(title: "", message: "Choose an option", preferredStyle: .actionSheet)
        actionSheet.addAction(UIAlertAction(title: "Camera", style: .default, handler: { (action:UIAlertAction) in
            if UIImagePickerController.isSourceTypeAvailable(.camera) {
                imagePickerController.sourceType = .camera
                imagePickerController.allowsEditing = true
                self.present(imagePickerController, animated: true, completion: nil)
            } else {
                let alert = UIAlertController(title: "Camera is not available", message: "Please select another option", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
                self.present(alert, animated: true, completion: nil)
            }
            
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Photo library", style: .default, handler: { (action:UIAlertAction) in
            imagePickerController.sourceType = .photoLibrary
            imagePickerController.allowsEditing = true
            self.present(imagePickerController, animated: true, completion: nil)
            
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        self.present(actionSheet, animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        var selectedImageFromPicker : UIImage?
        if let edit = info[.editedImage] as? UIImage {
            selectedImageFromPicker = edit
        } else if let original = info[.originalImage] as? UIImage {
            selectedImageFromPicker = original
        }
        
        if let selectedImage = selectedImageFromPicker {
            profilePictureImageView.image = selectedImage
            profilePictureImageView.contentMode = .scaleAspectFit
            saveAndProceedButton.isEnabled = true
        }
        
        picker.dismiss(animated: true, completion: nil)
        
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
    }
    
    //MARK:- Prepare for Segue
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "goToHomePage" {
            if let homeNC = segue.destination as? UINavigationController {
                if let destinationVC = homeNC.topViewController as? AppointmentListController {
                    destinationVC.student = student
                    destinationVC.userProfessor = userProfessor
                    destinationVC.userType = userType
                }
            }
        }
    }
    
    
    @IBAction func saveAndProceedButtonPressed(_ sender: Any) {
        SVProgressHUD.show()
        let data = profilePictureImageView.image?.jpegData(compressionQuality: 0.2) as! Data
        let storageRef = storage.reference()
        var profilePictureReference = storageRef.child("student/\(student.username).jpg")
        if userType != "Student" {
            profilePictureReference = storageRef.child("professor/\(userProfessor.profId).jpg")
        }
        
        let uploadTask = profilePictureReference.putData(data, metadata: nil) { (metadata, error) in
            
            guard let metadata = metadata else {
                return
            }
            
            if let error = error {
                print("Error: ", error)
            }
            
            let size = metadata.size
            
            profilePictureReference.downloadURL(completion: { (url, error) in
                guard let downloadURL = url else {
                    return
                }
                
                self.performSegue(withIdentifier: "goToHomePage", sender: self)
                if let error = error {
                    print("Error: ", error)
                }
                SVProgressHUD.dismiss()
            })
        }
        
        
    }
    
    

}
