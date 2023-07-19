//
//  QRCodeHelper.swift
//  iosApp
//
//  Created by Lito Bumba on 19/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import UIKit
import CoreImage.CIFilterBuiltins

class QRCodeHelperImpl: QRCodeHelperIos {
    
    func generate(text: String) -> UIImage {
        let context = CIContext()
        let filter = CIFilter.qrCodeGenerator()
        let data = Data(text.utf8)
        
        filter.setValue(data, forKey: "inputMessage")
        
        if let qrCodeImage = filter.outputImage {
            let scaleX = 512.0 / qrCodeImage.extent.size.width
            let scaleY = 512.0 / qrCodeImage.extent.size.height
            
            let transformedImage = qrCodeImage.transformed(by: CGAffineTransform(scaleX: scaleX, y: scaleY))
            
            if let qrCodeCGImage = context.createCGImage(transformedImage, from: transformedImage.extent){
                return UIImage(cgImage: qrCodeCGImage)
            }
        }
        
        return UIImage(systemName: "xmark") ?? UIImage()
    }
}
