'use strict';

describe('Controller Tests', function() {

    describe('Entrada Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEntrada, MockBeneficio, MockTipo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEntrada = jasmine.createSpy('MockEntrada');
            MockBeneficio = jasmine.createSpy('MockBeneficio');
            MockTipo = jasmine.createSpy('MockTipo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Entrada': MockEntrada,
                'Beneficio': MockBeneficio,
                'Tipo': MockTipo
            };
            createController = function() {
                $injector.get('$controller')("EntradaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coffeeShopApp:entradaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
