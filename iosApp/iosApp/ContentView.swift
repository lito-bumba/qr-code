import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    
    let qrCodeHelper = QRCodeHelperImpl()
    
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController(qrCodeHelperIos: qrCodeHelper)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



