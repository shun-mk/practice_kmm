import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: RocketLaunchViewModel
    var body: some View {
        NavigationView(content: {
            makeList()
                .navigationBarTitle("SpaceX Launches")
                .navigationBarItems(trailing: Button("Reload"){
                    viewModel.loadLaunches(forceReload: true)
                })
        })
    }

    private func makeList() -> AnyView {
        switch viewModel.launches {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let data):
            return AnyView(List(data) { launch in
                RocketLaunchRow(rocketLaunch: launch)
            })
        case .error(let desc):
            return AnyView(Text(desc).multilineTextAlignment(.center))
        }
    }
}
