import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Check, PackageCheck, PackageOpen } from 'lucide-react';

export function LandingKeyFeatures() {
  const features = [
    {
      icon: PackageOpen,
      title: 'Prêter vos outils',
      description: "Partagez vos outils dont vous n'avez plus besoin et aidez votre communauté.",
      benefits: ['Rentabilisez vos outils', 'Aidez votre voisinage', 'Réduisez le gaspillage'],
      color: 'from-red-500 to-orange-500',
    },
    {
      icon: PackageCheck,
      title: 'Emprunter facilement',
      description: 'Trouvez les outils dont vous avez besoin près de chez vous.',
      benefits: ["Économisez de l'argent", 'Accès instantané', "Variété d'outils"],
      color: 'from-orange-500 to-yellow-500',
    },
  ];

  return (
    <section id="key-features" className="w-full py-20 bg-white">
      <div className="container mx-auto px-4">
        <div className="text-center mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">Deux façons de participer</h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">Rejoignez une communauté qui partage et s'entraide</p>
        </div>

        <div className="grid gap-8 lg:grid-cols-2 max-w-6xl mx-auto">
          {features.map((feature) => (
            <Card
              key={feature.title}
              className="group hover:shadow-2xl transition-all duration-300 border-0 shadow-lg overflow-hidden"
            >
              <div className={`h-2 bg-gradient-to-r ${feature.color}`}></div>
              <CardHeader className="pb-4">
                <div
                  className={`w-16 h-16 rounded-2xl bg-gradient-to-r ${feature.color} flex items-center justify-center mb-4 group-hover:scale-110 transition-transform duration-300`}
                >
                  <feature.icon className="h-8 w-8 text-white" />
                </div>
                <CardTitle className="text-2xl font-bold text-gray-900">{feature.title}</CardTitle>
                <CardDescription className="text-lg text-gray-600">{feature.description}</CardDescription>
              </CardHeader>
              <CardContent>
                <ul className="space-y-3">
                  {feature.benefits.map((benefit, i) => (
                    <li key={i} className="flex items-center space-x-3">
                      <Check className="h-5 w-5 text-green-500 flex-shrink-0" />
                      <span className="text-gray-700">{benefit}</span>
                    </li>
                  ))}
                </ul>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
}
